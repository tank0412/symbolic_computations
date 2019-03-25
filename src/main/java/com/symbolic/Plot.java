package com.symbolic;

import static javafx.application.Application.launch;
import java.awt.Color;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.data.xyz.XYZSeries;
import com.orsoncharts.data.xyz.XYZSeriesCollection;
import com.orsoncharts.fx.Chart3DViewer;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.plot.XYZPlot;

import javax.swing.event.ChangeListener;

public class Plot extends Application {
    static XYZPlot plotGlobal = null;
    public static com.symbolic.Node data = null;

    public void makePlot(com.symbolic.Node dots) {
        data = dots;
        launch();

    }
    public static javafx.scene.Node createDemoNode() {
        XYZDataset dataset = createDataset();
        Chart3D chart = createChart(dataset);
        Chart3DViewer viewer = new Chart3DViewer(chart);
        return viewer;
    }

    private static XYZDataset<String> createDataset() {
        XYZSeriesCollection<String> dataset = new XYZSeriesCollection<>();
        XYZSeries<String> input;
        int index = 0;
        if(Parse.context != null) {
            Transform transform = new Transform();
            data = transform.solve(5);
        }
            for (com.symbolic.Node node : data.arguments) {
                input = new XYZSeries<>(Integer.toString(index));
                for (com.symbolic.Node node2 : node.arguments) {
                    input.add(((Digit) node2.arguments.get(0)).value, ((Digit) node2.arguments.get(1)).value, ((Digit) node2.arguments.get(2)).value); // add x, y, z from plot arguments
                }
                dataset.add(input);
                index++;
            }
        return dataset;
    }

    private static Chart3D createChart(XYZDataset dataset) {
        Chart3D chart = Chart3DFactory.createXYZLineChart("Symbolic computations",
                "Lab4", dataset, "X", "Y", "Z");
        chart.setChartBoxColor(new Color(255, 255, 255, 128));
        XYZPlot plot = (XYZPlot) chart.getPlot();
        plot.setDimensions(new Dimension3D(15, 10, 8));
        plotGlobal = plot;
        return chart;
    }

    @Override
    public void start(Stage stage) throws Exception {
        StackPane sp = new StackPane();
        sp.getChildren().add(createDemoNode());
        Scene scene = new Scene(sp, 768, 512);
        stage.setScene(scene);
        stage.setTitle("Lab4");
        stage.show();
        InvalidationListener stageSizeListener = (observable) -> {
            //System.out.println("Height" + stage.getHeight() + "Width" + stage.getWidth());
            changePlotDimension(stage.getHeight(), stage.getWidth());
        };
        stage.widthProperty().addListener(stageSizeListener);
        stage.heightProperty().addListener(stageSizeListener);
    }
    public static void changePlotDimension(double newHeight, double newWidth) {
        double widthPlot = 15;
        double heightPlot = 10;
        double widthStage = 768;
        double heightStage = 512;
        double k = 0;
        k = newWidth / widthStage;
        widthPlot *=k;
        k = newHeight / heightStage;
        heightPlot*=k;
        plotGlobal.setDimensions(new Dimension3D(widthPlot, heightPlot, 8));
    }
}
