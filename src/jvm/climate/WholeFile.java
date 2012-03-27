package climate;

import cascading.flow.hadoop.HadoopFlowProcess;
import cascading.scheme.Scheme;
import cascading.scheme.SinkCall;
import cascading.scheme.SourceCall;
import cascading.tap.Tap;
import cascading.tuple.Fields;
import cascading.tuple.Tuple;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.RecordReader;

import java.io.IOException;

public class WholeFile extends Scheme<HadoopFlowProcess, JobConf, RecordReader, OutputCollector, Object[], Object[]> {
    public WholeFile( Fields fields ) {
        super(fields);
    }

    @Override
    public void sourceConfInit(HadoopFlowProcess hadoopFlowProcess, Tap tap, JobConf conf) {
        conf.setInputFormat( WholeFileInputFormat.class );
    }

    @Override public void sourcePrepare(HadoopFlowProcess flowProcess,
        SourceCall<Object[], RecordReader> sourceCall) {

        sourceCall.setContext(new Object[2]);

        sourceCall.getContext()[0] = sourceCall.getInput().createKey();
        sourceCall.getContext()[1] = sourceCall.getInput().createValue();
    }

    @Override public boolean source(HadoopFlowProcess hadoopFlowProcess,
        SourceCall<Object[], RecordReader> sourceCall) throws IOException {


        Text key = (Text) sourceCall.getContext()[0];
        BytesWritable value = (BytesWritable) sourceCall.getContext()[1];

        boolean result = sourceCall.getInput().next(key, value);

        if (!result)
            return false;

        sourceCall.getIncomingEntry().setTuple(new Tuple(key.toString(), value));
        return true;
    }

    @Override
    public void sinkConfInit(HadoopFlowProcess hadoopFlowProcess, Tap tap, JobConf conf) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override public void sink(HadoopFlowProcess hadoopFlowProcess,
        SinkCall<Object[], OutputCollector> outputCollectorSinkCall) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
} 
