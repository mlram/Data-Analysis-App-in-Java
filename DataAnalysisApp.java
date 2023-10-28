//Importing dependencies and libraries
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYSeries;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

// // Declaration of instance variables to represent various GUI components
public class DataAnalysisApp {
    private JFrame frame;
    private JTable dataTable;
    private JButton loadButton;
    private JButton resetButton;
    private JButton barChartButton;
    private JButton pieChartButton;
    private JButton lineChartButton;
    private JComboBox<String> columnSelector;
    private ChartPanel chartPanel;
    private CustomGraphicsPanel customGraphicsPanel;
    private JPanel chatPanel;
    private JTextArea chatTextArea;
    private JTextField chatInputField;
    private Chatbot Chatbot = new Chatbot();

    //// A DefaultTableModel to store the original data loaded from a CSV file
    private DefaultTableModel originalModel;

    private Color customGraphicsColor = Color.BLACK;
    private BasicStroke customGraphicsStroke = new BasicStroke(2.0f);
    private String customGraphicsText = "";

    // Constructor for the DataAnalysisApp class
    public DataAnalysisApp() {
        frame = new JFrame("DataGuru");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setBackground(Color.BLUE);

        dataTable = new JTable();
        JScrollPane tableScrollPane = new JScrollPane(dataTable);

        loadButton = new JButton("Load Data");
        resetButton = new JButton("Reset");
        barChartButton = new JButton("Bar Chart");
        pieChartButton = new JButton("Pie Chart");
        lineChartButton = new JButton("Line Chart");
        columnSelector = new JComboBox<>();
        chartPanel = createChartPanel();
        customGraphicsPanel = new CustomGraphicsPanel();
        chatPanel = createChatPanel();

        // Adding an ActionListener to the loadButton to handle button clicks
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadCSVData();
            }
        });
       
        // Adding an ActionListener to the resetButton to handle button clicks
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetApplication();
            }
        });
      // Adding an ActionListener to the barChartButton to handle button clicks
        barChartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createBarChart();
            }
        });

        // Adding an ActionListener to the pieChartButton to handle button clicks
        pieChartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createPieChart();
            }
        });
       //// Adding an ActionListener to the lineChartButton to handle button clicks
        lineChartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createLineChart();
            }
        });
// Adding an ItemListener to the columnSelector JComboBox to handle item selection
        columnSelector.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateFilteringOptions();
                }
            }
        });

        // Inside the constructor, add KeyEvent and FocusEvent listeners to chatInputField
chatInputField.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        sendMessage();
    }
});
chatInputField.addFocusListener(new FocusAdapter() {
    @Override
    public void focusGained(FocusEvent e) {
        chatInputField.requestFocus();
    }
});
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(loadButton);
        topPanel.add(columnSelector);
        topPanel.add(resetButton);
        topPanel.add(barChartButton);
        topPanel.add(pieChartButton);
        topPanel.add(lineChartButton);

        // Adding custom graphics options
        JPanel customGraphicsOptions = createCustomGraphicsOptionsPanel();
        topPanel.add(customGraphicsOptions);
        topPanel.setBackground(new Color(235, 241, 245)); 
        frame.setLayout(new BorderLayout());

        // Create a new panel for the four major panels with GridLayout
        JPanel mainPanel = new JPanel(new GridLayout(2, 2));
        mainPanel.add(tableScrollPane);
        mainPanel.add(chartPanel);
        mainPanel.add(customGraphicsPanel);
        mainPanel.add(chatPanel);
        mainPanel.setBackground(new Color(12, 142, 235));

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
// Creating a ChartPanel for displaying various types of charts
    private ChartPanel createChartPanel() {
        JFreeChart chart = ChartFactory.createBarChart(
            "Default Chart",
            "Category",
            "Value",
            new DefaultCategoryDataset(),
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
        return new ChartPanel(chart);
    }

    // Loading data from a CSV file into the application
    private void loadCSVData() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a CSV file");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));

        int userSelection = fileChooser.showOpenDialog(this.frame);
//Applying conditions to check if the user has selected a file
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            originalModel = new DefaultTableModel();

            try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                Vector<String> columnNames = new Vector<>();

                if ((line = br.readLine()) != null) {
                    String[] columns = line.split(",");
                    for (String column : columns) {
                        originalModel.addColumn(column);
                        columnNames.add(column);
                    }
                }

                while ((line = br.readLine()) != null) {
                    String[] rowData = line.split(",");
                    originalModel.addRow(rowData);
                }

                columnSelector.setModel(new DefaultComboBoxModel<>(columnNames));
            } catch (IOException e) {
                e.printStackTrace();
            }

            dataTable.setModel(originalModel);
        }
    }
// Resetting the application
    private void resetApplication() {
        dataTable.setModel(new DefaultTableModel());
        chartPanel.setChart(null);
        columnSelector.setModel(new DefaultComboBoxModel<>());
        customGraphicsPanel.clearCustomGraphics();
        frame.revalidate();
    }
// Creating a bar chart
    private void createBarChart() {
        if (originalModel != null) {
            DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
            int selectedColumnIndex = columnSelector.getSelectedIndex();

            //Creating a DefaultCategoryDataset to store the data
            DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();

            //Iterating through the rows of the table
            for (int row = 0; row < model.getRowCount(); row++) {
                String category = (String) model.getValueAt(row, 0);
                String value = (String) model.getValueAt(row, selectedColumnIndex);

                try {
                    double numericValue = Double.parseDouble(value);
                    categoryDataset.addValue(numericValue, "Value", category);
                } catch (NumberFormatException e) {
                    // Ignore non-numeric values
                }
            }

            JFreeChart chart = ChartFactory.createBarChart(
                "Bar Chart",
                "Category",
                "Value",
                categoryDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
            );

            chartPanel.setChart(chart);
            frame.revalidate();
        }
    }

    // Creating a pie chart
    private void createPieChart() {
        if (originalModel != null) {
            DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
            int selectedColumnIndex = columnSelector.getSelectedIndex();

            DefaultPieDataset pieDataset = new DefaultPieDataset();

            //Iterating through the rows of the table
            for (int row = 0; row < model.getRowCount(); row++) {
                String category = (String) model.getValueAt(row, 0);
                String value = (String) model.getValueAt(row, selectedColumnIndex);

                try {
                    double numericValue = Double.parseDouble(value);
                    pieDataset.setValue(category, numericValue);
                } catch (NumberFormatException e) {
                    // Ignore non-numeric values
                }
            }
//Creating a JFreeChart object
            JFreeChart chart = ChartFactory.createPieChart(
                "Pie Chart",
                pieDataset,
                true,
                true,
                false
            );

            chartPanel.setChart(chart);
            frame.revalidate();
        }
    }

    // Creating a line chart
    private void createLineChart() {
        if (originalModel != null) {
            DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
            int selectedColumnIndex = columnSelector.getSelectedIndex();
            DefaultXYDataset xyDataset = new DefaultXYDataset();
            XYSeries series = new XYSeries("Line Chart");

            for (int row = 0; row < model.getRowCount(); row++) {
                String value = (String) model.getValueAt(row, selectedColumnIndex);

                try {
                    double numericValue = Double.parseDouble(value);
                    series.add(row, numericValue);
                } catch (NumberFormatException e) {
                    // Ignore non-numeric values
                }
            }

            double[] xValues = new double[series.getItemCount()];
            double[] yValues = new double[series.getItemCount()];

            for (int i = 0; i < series.getItemCount(); i++) {
                xValues[i] = series.getX(i).doubleValue();
                yValues[i] = series.getY(i).doubleValue();
            }

            xyDataset.addSeries("Line Chart", new double[][] { xValues, yValues });

            JFreeChart chart = ChartFactory.createXYLineChart(
                "Line Chart",
                "Category",
                "Value",
                xyDataset
            );

            chartPanel.setChart(chart);
            frame.revalidate();
        }
    }

    // Updating the filtering options
    private void updateFilteringOptions() {
        if (originalModel != null) {
            // Implement filtering options here if needed
        }
    }

    // Creating a chat panel
    private JPanel createChatPanel() {

// Creating a JScrollPane for the chatTextArea
JScrollPane scrollPane = new JScrollPane(chatTextArea);
scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        e.getAdjustable().setValue(e.getAdjustable().getMaximum());
    }
});



// Creating a JPanel for the chatPanel
        JPanel chatPanel = new JPanel(new BorderLayout());
        chatPanel.setPreferredSize(new Dimension(750, 400));
       

        // Creating a JTextArea for the chatTextArea
        chatTextArea = new JTextArea();
        chatTextArea.setEditable(false);
        chatTextArea.setWrapStyleWord(true);
        chatTextArea.setLineWrap(true);
        
        // Adding a JScrollPane to the chatTextArea
        chatPanel.setBackground(new Color(245, 252, 255));
        chatPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(245, 252, 255)), // Top border
        BorderFactory.createEmptyBorder(10, 10, 10, 10) // Padding
));

        JScrollPane scrollPaneTextArea = new JScrollPane(chatTextArea);
        chatPanel.add(scrollPaneTextArea, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        chatInputField = new JTextField();
        chatInputField.setToolTipText("Type your message here...");
        JButton sendButton = new JButton("Send");
        sendButton.setBackground(new Color(255, 252, 255));
        sendButton.setBorderPainted(true);


        // Adding an ActionListener to the sendButton to handle button clicks
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        inputPanel.add(chatInputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        chatPanel.add(inputPanel, BorderLayout.SOUTH);

        return chatPanel;
    }

    // Sending a message
public void sendMessage() {
    String userInput = chatInputField.getText();
    if (!userInput.isEmpty()) {
        chatInputField.setText("");
        chatTextArea.append("You: " + userInput + "\n");
        String response = Chatbot.generateResponse(userInput, originalModel);
        chatTextArea.append("Chatbot: " + response + "\n");
        chatTextArea.setBackground(new Color(255, 252, 255));

        // Applying subtle gradients to the chatTextArea
        chatTextArea.setOpaque(false); // To make text area transparent
        chatTextArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding
        chatTextArea.setForeground(Color.BLACK); // Text color
        chatTextArea.setFont(new Font("Arial", Font.PLAIN, 14)); // Font
    }

}
    // Creating a custom graphics options panel
    private JPanel createCustomGraphicsOptionsPanel() {
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Custom Graphics Options");
        optionsPanel.add(titleLabel);
        optionsPanel.setBackground(new Color(245, 252, 255));
        optionsPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(245, 252, 255)), // Right border
        BorderFactory.createEmptyBorder(10, 10, 10, 10) // Padding
));       
        JPanel colorPanel = new JPanel();
        colorPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel colorLabel = new JLabel("Color: ");
        JButton colorButton = new JButton("Choose Color");
        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customGraphicsColor = JColorChooser.showDialog(null, "Choose Color", customGraphicsColor);
                repaintCustomGraphics();
            }
        });
        colorPanel.add(colorLabel);
        colorPanel.add(colorButton);

        optionsPanel.add(colorPanel);

        JPanel thicknessPanel = new JPanel();
        thicknessPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel thicknessLabel = new JLabel("Line Thickness: ");
        JSlider thicknessSlider = new JSlider(1, 10, 2);
        thicknessSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                customGraphicsStroke = new BasicStroke(thicknessSlider.getValue());
                repaintCustomGraphics();
            }

        });
        // Adding a ChangeListener to the thicknessSlider to handle slider changes
        thicknessPanel.add(thicknessLabel);
        thicknessPanel.add(thicknessSlider);

        optionsPanel.add(thicknessPanel);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        JLabel textLabel = new JLabel("Annotation Text: ");
        JTextField textInput = new JTextField(6);
        JButton applyTextButton = new JButton("Apply Text");
        applyTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customGraphicsText = textInput.getText();
                repaintCustomGraphics();
            }
        });
        textPanel.add(textLabel);
        textPanel.add(textInput);
        textPanel.add(applyTextButton);

        optionsPanel.add(textPanel);

        return optionsPanel;
    }

    // Repainting custom graphics
    private void repaintCustomGraphics() {
        customGraphicsPanel.clearCustomGraphics();
        for (Shape shape : customGraphicsPanel.customShapes) {
            customGraphicsPanel.addCustomShape(shape);
        }

        frame.revalidate();
    }
// Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DataAnalysisApp();
            }
        });
    }
// Creating a custom graphics panel
    class CustomGraphicsPanel extends JPanel {
        private ArrayList<Shape> customShapes;
        private Path2D currentPath;
        
        public CustomGraphicsPanel() {
            customShapes = new ArrayList<>();
            setPreferredSize(new Dimension(750, 150));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    currentPath = new Path2D.Double();
                    currentPath.moveTo(e.getX(), e.getY());
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    customShapes.add(currentPath);
                    currentPath = null;
                    repaint();
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (currentPath != null) {
                        currentPath.lineTo(e.getX(), e.getY());
                        repaint();
                    }
                }
            });
        }

        public void addCustomShape(Shape shape) {
            customShapes.add(shape);
            repaint();
        }

        public void clearCustomGraphics() {
            customShapes.clear();
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            for (Shape shape : customShapes) {
                g2d.setColor(customGraphicsColor);
                g2d.setStroke(customGraphicsStroke);
                g2d.draw(shape);

                if (!customGraphicsText.isEmpty() && shape instanceof Rectangle2D) {
                    Rectangle2D rect = (Rectangle2D) shape;
                    FontMetrics metrics = g.getFontMetrics();
                    int x = (int) (rect.getX() + rect.getWidth() / 2 - metrics.stringWidth(customGraphicsText) / 2);
                    int y = (int) (rect.getY() + rect.getHeight() / 2 + metrics.getHeight() / 2);
                    g2d.drawString(customGraphicsText, x, y);
                }
            }

            if (currentPath != null) {
                g2d.setColor(customGraphicsColor);
                g2d.setStroke(customGraphicsStroke);
                g2d.draw(currentPath);
                g2d.create();
                g2d.drawImage(null, 10, 10, null);
                g2d.drawPolygon(null);
            }
        }
    }
}
