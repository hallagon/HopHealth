package com.akim77.hopkinshealth;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static java.util.Collections.max;

public class CombinedActiveHoursChartActivity extends AppCompatActivity {
    final float DOTSIZE = 20f;
    private ArrayList<Float> hoursArray = new ArrayList<Float>();
    private Button returnButton;
    private ArrayList<FeedbackFragment.WeeklyFeedbackData> feedbackList;
    private int lastWeekNumber = 0;
    private TextView lastWeekTextView;


    private void generateStepsData(){
        float[] steps = new float[12];
        for (int i = 0; i < 12; i++){
            steps[i] = -1f;
        }
        for (float i : steps){
            hoursArray.add(i);
        }
    }

    private void organizeHoursData(){
        feedbackList = (ArrayList<FeedbackFragment.WeeklyFeedbackData>) getIntent().getSerializableExtra("feedbackList");
        Collections.sort(feedbackList, new Comparator<FeedbackFragment.WeeklyFeedbackData>(){
            public int compare(FeedbackFragment.WeeklyFeedbackData o1, FeedbackFragment.WeeklyFeedbackData o2){
                return o1.week - o2.week;
            }
        });
        Log.d("debuggingactivehours", feedbackList.toString());
        for(FeedbackFragment.WeeklyFeedbackData data : feedbackList){
//            weightsArray.add(data.avg_weight);

            hoursArray.set(data.week - 1, data.total_active_min / 60f);
        }
        lastWeekNumber = feedbackList.size();
        float lastHours;
        if(lastWeekNumber > 0) lastHours = hoursArray.get(lastWeekNumber - 1);
        else lastHours = 0;
        Log.d("lasthours1", lastHours + "");

        lastHours = (int) (lastHours * 100) / 100f;
        Log.d("lasthours2", lastHours + "");
        lastWeekTextView.setText("Last Week: " + lastHours + " hours of activity");

    }

    private void getSteps(){

    }


    private CombinedChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combined_active_hours_chart);
        returnButton = findViewById(R.id.active_hours_return_button);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        lastWeekTextView = findViewById(R.id.active_hours_textview);
        generateStepsData();
        organizeHoursData();
        setupChart();

    }

    protected void setupChart(){
        mChart = findViewById(R.id.active_chart);
        mChart.getDescription().setEnabled(false);
//        mChart.setBackgroundColor(getResources().getColor(R.color.over_red));
        mChart.setDrawGridBackground(true);
        mChart.setGridBackgroundColor(getResources().getColor(R.color.chart_green));
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

        Legend l = mChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
//        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        rightAxis.setDrawAxisLine(false);
        rightAxis.setDrawLabels(false);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        float maxHours = Collections.max(hoursArray);
        leftAxis.setAxisMaximum(maxHours > 5 ? maxHours : 5);
        leftAxis.setSpaceBottom(0f);
        leftAxis.setGranularity(1f);
//        leftAxis.setLabelCount(7, false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setTextSize(15f);

        LimitLine ll1 = new LimitLine(3f, "Target");
        ll1.setLineWidth(0f);
//        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.CENTER_TOP);
        ll1.setTextSize(100f);
        ll1.setTextColor(getResources().getColor(R.color.chart_dark_green));
        leftAxis.addLimitLine(ll1);
        leftAxis.setDrawLimitLinesBehindData(true);


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
        if(d != null) setupLegends(d.getDataSets());
        data.setData(d);
//        data.setData(generateCandleData());
        data.setData(generateBarData());

//        data.setValueTypeface(mTfLight);

//        xAxis.setAxisMaximum(data.getXMax() + 0.25f);
//        mChart.setDrawGridBackground(true);
        mChart.setData(data);
        mChart.invalidate();
    }

    protected void setupLegends(List<IScatterDataSet> dataSets){
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

        for (int i = 0; i < hoursArray.size(); i++) {
            if(hoursArray.get(i) > 0) {
                entries.add(new Entry(i + 1, hoursArray.get(i)));
            } else {
                redEntries.add(new Entry(i + 1, hoursArray.get(i)));
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

//        ArrayList<BarEntry> entries1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> entries2 = new ArrayList<BarEntry>();

        /*
        for (int index = 0; index < itemcount; index++) {
            entries1.add(new BarEntry(0, 25));

            // stacked
            entries2.add(new BarEntry(0, new float[]{13, 13}));
        }*/
        entries2.add(new BarEntry(0, 3f));


//        BarDataSet set1 = new BarDataSet(entries1, "Bar 1");
//        set1.setColor(Color.rgb(60, 220, 78));
//        set1.setValueTextColor(Color.rgb(60, 220, 78));
//        set1.setValueTextSize(10f);
//        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        BarDataSet set2 = new BarDataSet(entries2, "");
//        set2.setStackLabels(new String[]{"Stack 1", "Stack 2"});
//        set2.setColors(new int[]{Color.rgb(61, 165, 255), Color.rgb(23, 197, 255)});
        set2.setColor(getResources().getColor(R.color.chart_red));
        set2.setValueTextColor(Color.rgb(61, 165, 255));

        set2.setValueTextSize(10f);
        set2.setHighlightEnabled(false);

        set2.setAxisDependency(YAxis.AxisDependency.LEFT);

        float barWidth = (hoursArray.size() + 1) * 2f; // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

        BarData d = new BarData(set2);
        d.setBarWidth(barWidth);
        d.setValueTextSize(0f);

        // make this BarData object grouped
//        d.groupBars(0, groupSpace, barSpace); // start at x = 0

        return d;
    }


}