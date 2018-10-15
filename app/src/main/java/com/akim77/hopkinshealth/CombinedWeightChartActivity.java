package com.akim77.hopkinshealth;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.akim77.hopkinshealth.fragments.FeedbackFragment;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class CombinedWeightChartActivity extends AppCompatActivity {
    final float DOTSIZE = 20f;
    final float KGTOLBS = 2.20462f;
    private int lastWeekNumber = 0;
    private ArrayList<Float> weightsArray = new ArrayList<Float>();
    private Button returnButton;
    private float heightInMeters = 0.5f;
    private ArrayList<FeedbackFragment.WeeklyFeedbackData> feedbackList;

    private void generateWeightData(){
        Random rand = new Random();
        rand.nextInt(15000);
        float[] weights = new float[12];
        for (int i = 0; i < 12; i++){
//            weights[i] = (float)rand.nextInt(100) + 130;
            weights[i] = (float) -1;
        }
//        weights[5] = 0;
        for (float i : weights){
            weightsArray.add(i);
        }
    }

    private void organizeWeightData(){
        feedbackList = (ArrayList<FeedbackFragment.WeeklyFeedbackData>) getIntent().getSerializableExtra("feedbackList");
        Collections.sort(feedbackList, new Comparator<FeedbackFragment.WeeklyFeedbackData>(){
            public int compare(FeedbackFragment.WeeklyFeedbackData o1, FeedbackFragment.WeeklyFeedbackData o2){
                return o1.week - o2.week;
            }
        });
        Log.d("debuggingweights", feedbackList.toString());
        for(FeedbackFragment.WeeklyFeedbackData data : feedbackList){
            weightsArray.set(data.week - 1, data.avg_weight * KGTOLBS);
            heightInMeters = data.height / 100;
        }
        lastWeekNumber = feedbackList.size();

    }

    private float calculateBMI(float weightInKg, float height){
        float bmi = weightInKg / (height * height);
        return bmi;
    }

    private float[] getBmiWeightZones(float height){
        float[] bmiZones = new float[3];
        float heightSquared = height * height;
        bmiZones[0] = heightSquared * 18.5f * KGTOLBS;
        bmiZones[1] = heightSquared * 25f * KGTOLBS;
        bmiZones[2] = heightSquared * 30f * KGTOLBS;

        return bmiZones;
    }

    private CombinedChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combined_weight_chart);
        returnButton = findViewById(R.id.weight_return_button);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        generateWeightData();
        organizeWeightData();
        setupChart();

    }

    protected void setupChart(){
        mChart = findViewById(R.id.weightChart);
        mChart.getDescription().setEnabled(false);
//        mChart.setBackgroundColor(getResources().getColor(R.color.over_red));
        mChart.setDrawGridBackground(true);
        mChart.setGridBackgroundColor(getResources().getColor(R.color.chart_red));
        mChart.setDrawBarShadow(false);
        mChart.setScaleEnabled(false);
        mChart.setHighlightFullBarEnabled(false);
        mChart.setDrawBorders(false);
        mChart.getLegend().setEnabled(true);
        mChart.setExtraOffsets(0f, 10f, 10f, 10f);

        // draw bars behind lines
        mChart.setDrawOrder(new DrawOrder[]{
                DrawOrder.BAR, DrawOrder.BUBBLE, DrawOrder.CANDLE, DrawOrder.LINE, DrawOrder.SCATTER
        });

        if(weightsArray.get(0) < 0) {
            Legend l = mChart.getLegend();
            l.setWordWrapEnabled(true);
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
            l.setOrientation(Legend.LegendOrientation.VERTICAL);
            l.setDrawInside(true);
        }

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawLabels(false);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        int weightMaxThreshold = ((int) (getBmiWeightZones(heightInMeters)[2] * 2 / 100)) * 100;
        float wmt = weightMaxThreshold * 0.75f;
        leftAxis.setAxisMaximum(wmt);
        Log.d("weightmax", "" + wmt);
        leftAxis.setSpaceBottom(0f);

//        leftAxis.setGranularity(getBmiWeightZones(heightInMeters)[1] - getBmiWeightZones(heightInMeters)[0]);
//        leftAxis.setGranularity(100f);
//        leftAxis.setGranularityEnabled(true);
//        leftAxis.setLabelCount(6, false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setTextSize(15f);

        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String stringVal;

                stringVal = String.valueOf((int)value);


                return stringVal;
            }

        });
/*
        LimitLine ll1 = new LimitLine(120f, "Target");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.CENTER_TOP);
        ll1.setTextSize(100f);
        ll1.setTextColor(getResources().getColor(R.color.over_green_dark));
        */

        LimitLine ll1 = new LimitLine(getBmiWeightZones(heightInMeters)[0], "Underweight");
        LimitLine ll2 = new LimitLine(getBmiWeightZones(heightInMeters)[1], "Healthy");
        LimitLine ll3 = new LimitLine(getBmiWeightZones(heightInMeters)[2], "Overweight");
        LimitLine ll4 = new LimitLine(getBmiWeightZones(heightInMeters)[2], "Obese");
        int[] darkColors = new int[]{
                getResources().getColor(R.color.chart_dark_blue),
                getResources().getColor(R.color.chart_dark_green),
                getResources().getColor(R.color.chart_dark_yellow),
                getResources().getColor(R.color.chart_dark_red)
        };
        LimitLine[] limitLines = new LimitLine[]{ll1, ll2, ll3, ll4};
        int count = 0;
        for (LimitLine ll : limitLines){
            ll.setLineWidth(0f);
            ll.setLineColor(darkColors[count]);
//            ll.enableDashedLine(10f, 10f, 0f);
            ll.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
            ll.setTextSize(30f);
            ll.setTextColor(darkColors[count++]);
        }
        ll4.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll4.setLineColor(Color.TRANSPARENT);


        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.addLimitLine(ll3);
        leftAxis.addLimitLine(ll4);

//        leftAxis.setDrawLimitLinesBehindData(true);


        XAxis xAxis = mChart.getXAxis();
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setAxisMaximum(13f);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(13);
        xAxis.setLabelRotationAngle(-45);
        xAxis.setTextSize(18f);
        xAxis.setCenterAxisLabels(false);
        xAxis.setCustomAxisOffsetEnabled(true);
//        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(value < 1 || value > 12) return "";
                if(value == lastWeekNumber) return "last week";
                return "week " + (int)value;
            }
        });

        CombinedData data = new CombinedData();

        ScatterData d =generateScatterData();
        data.setData(d);
        data.setData(generateBarData());
        if(d != null) {
            setupLegends(d.getDataSets());
        }


//        data.setValueTypeface(mTfLight);

//        xAxis.setAxisMaximum(data.getXMax() + 0.25f);
//        mChart.setDrawGridBackground(true);
        mChart.setData(data);
        mChart.invalidate();
    }

    protected void setupLegends(List<IScatterDataSet> dataSets){
        if(dataSets == null) return;
        List<LegendEntry> entries = new ArrayList<>();
        for (int i = 0; i < dataSets.size(); i++) {
            final LegendEntry entry = new LegendEntry();
            IScatterDataSet set = dataSets.get(i);
            String Label = set.getLabel();

            if(Label.equalsIgnoreCase("No Data")) {
                entry.form = Legend.LegendForm.CIRCLE;
                entry.formColor = set.getColor();
                entry.label = " " + Label;
                entries.add(entry);
            }
        }

        LegendEntry[] mEntries = entries.toArray(new LegendEntry[entries.size()]);

        Legend l = mChart.getLegend();
        l.setCustom(mEntries);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);
        l.setXOffset(10f);
        l.setYOffset(-15f);
        l.setWordWrapEnabled(true);
//        l.setXOffset(10f);
    }

    protected ScatterData generateScatterData() {
        ScatterData d = new ScatterData();

        ArrayList<Entry> entries = new ArrayList<Entry>();
        ArrayList<Entry> redEntries = new ArrayList<Entry>();

        Log.d("weightsarray", weightsArray.toString());

        if(weightsArray.get(0) == -1f) return null;

        for (int i = 0; i < weightsArray.size(); i++) {
            if(weightsArray.get(i) > 0) {
                entries.add(new Entry(i + 1, weightsArray.get(i)));
            } else {
                redEntries.add(new Entry(i + 1, weightsArray.get(i)));
            }
        }
        if(entries.size() < 1) return null;

        ScatterDataSet set = new ScatterDataSet(entries, "Recorded Steps");
//        set.setColors(ColorTemplate.material);
        set.setColor(Color.DKGRAY);
        set.setScatterShapeSize(DOTSIZE);
        set.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        set.setDrawValues(false);
        set.setValueTextSize(10f);
        set.setHighLightColor(Color.BLUE);

        ScatterDataSet redSet = new ScatterDataSet(redEntries, "No Data");
        redSet.setColor(Color.RED);
        redSet.setScatterShapeSize(DOTSIZE);
        redSet.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        redSet.setDrawValues(false);
        redSet.setHighLightColor(Color.BLUE);

        d.addDataSet(set);
        if(redEntries.size() > 0) {
            d.addDataSet(redSet);
        }


        return d;
    }

    private BarData generateBarData() {


        ArrayList<BarEntry> entries2 = new ArrayList<BarEntry>();
        float[] bmiZones = getBmiWeightZones(heightInMeters);
        entries2.add(new BarEntry(0, new float[]{
                bmiZones[0],
                bmiZones[1] - bmiZones[0],
                bmiZones[2] - bmiZones[1]}));


        BarDataSet set2 = new BarDataSet(entries2, "");
//        set2.setStackLabels(new String[]{"Stack 1", "Stack 2"});
        set2.setColors(getChartColors());
//        set2.setColor(getResources().getColor(R.color.over_red));
        set2.setValueTextColor(Color.rgb(61, 165, 255));
//        set2.setValueFormatter(new IValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
//
//                return "UNDERWIEHGT";
//            }
//        });


        set2.setValueTextSize(10f);
        set2.setHighlightEnabled(false);

        set2.setAxisDependency(YAxis.AxisDependency.LEFT);

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = (weightsArray.size() + 1) * 2f; // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

        BarData d = new BarData(set2);
        d.setBarWidth(barWidth);
        d.setValueTextSize(0f);

        // make this BarData object grouped
//        d.groupBars(0, groupSpace, barSpace); // start at x = 0


        return d;
    }

    private void generateLimitLines(LimitLine ll, int index){

        ll.setLineWidth(4f);
        ll.enableDashedLine(10f, 10f, 0f);
        ll.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll.setTextSize(100f);
        ll.setTextColor(getResources().getColor(R.color.colorButtonSelectedDark));

    }

    private int[] getChartColors(){
        return new int[]{getResources().getColor(R.color.chart_blue),
                getResources().getColor(R.color.chart_green),
                getResources().getColor(R.color.chart_yellow)
//                getResources().getColor(R.color.chart_red)
        };
    }


}