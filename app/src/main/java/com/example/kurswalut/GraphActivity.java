package com.example.kurswalut;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphActivity extends AppCompatActivity {

    GraphView graph;
    TextView cur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_layout);

        graph = (GraphView) findViewById(R.id.graph);
        cur = (TextView) findViewById(R.id.textViewCur);

        try {
            getExchangeRates();
        } catch (Exception e) {
            e.printStackTrace();
        }





    }

    public void getExchangeRates() throws Exception {

        // ConnectToNBP connectToNBP = new ConnectToNBP();
        RootObjectSchema rootObjectSchema;

        Bundle bundle = getIntent().getExtras();
        cur.setText(bundle.getString("currency").toUpperCase());

        rootObjectSchema = new ConnectToNBPSchema().execute(bundle.getString("currency")).get();


        setExchangeRates(rootObjectSchema);



    }



    private void setExchangeRates(RootObjectSchema data) {

        String[] xLabels = new String[data.getRates().length];
        String[] yLabels = new String[data.getRates().length];
        for(int i = 0; i < data.getRates().length; i++) {
            xLabels[i] = data.getRates()[i].getEffectiveDate();
            System.out.println("x"+i+"= "+xLabels[i]);
            yLabels[i] = data.getRates()[i].getMid();
            System.out.println("y"+i+"= "+yLabels[i]);
        }

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(xLabels);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(90);
        graph.getGridLabelRenderer().setLabelsSpace(15);
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.parseColor("#FF0000"));
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.parseColor("#FF0000"));


        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0,Double.parseDouble(yLabels[0])),
                new DataPoint(1,Double.parseDouble(yLabels[1])),
                new DataPoint(2,Double.parseDouble(yLabels[2])),
                new DataPoint(3,Double.parseDouble(yLabels[3])),
                new DataPoint(4,Double.parseDouble(yLabels[4])),
                new DataPoint(5,Double.parseDouble(yLabels[5])),
                new DataPoint(6,Double.parseDouble(yLabels[6])),
                new DataPoint(7,Double.parseDouble(yLabels[7])),
                new DataPoint(8,Double.parseDouble(yLabels[8])),
                new DataPoint(9,Double.parseDouble(yLabels[9])),
        });

        graph.addSeries(series);


    }

}
