// Creating a chatbot

import javax.swing.table.DefaultTableModel;

public class Chatbot {
        private static boolean isGreeting = true;
    
        public String generateResponse(String userInput, DefaultTableModel originalModel) {
            String response;
    
            if (isGreeting) {
                response = "Hello! Welcome to DataGuru, I am your Data Assistant.";
                isGreeting = false;
            } else if (userInput.contains("help")) {
                response = "Certainly! Here are some things you can do with this app:\n"
                        + "1. Load Data: Import a CSV file to start analyzing data.\n"
                        + "2. Choose a Column: Select a column to analyze from the dropdown.\n"
                        + "3. Reset: Clear data and charts with the 'Reset' button.\n"
                        + "4. Charts: Visualize data using 'Bar Chart,' 'Pie Chart,' or 'Line Chart' buttons.\n"
                        + "5. Custom Graphics: Get creative with custom graphics on the right panel.\n"
                        + "6. For more, just ask!\n";

            } else if (userInput.contains("What can I do with this app?")) {
                response = "This app is designed for data analysis. You can load data, visualize it, and even create custom graphics.";
            } else if (userInput.contains("load data")) {
                response = "To load data, click 'Load Data' and select a CSV file.";
            } else if (userInput.contains("reset")) {
                response = "Reset the app by clicking the 'Reset' button.";
            } else if (userInput.contains("bar chart")) {
                response = "Create a bar chart by selecting a column and clicking 'Bar Chart'.";
            } else if (userInput.contains("pie chart")) {
                response = "Generate a pie chart by selecting a column and clicking 'Pie Chart'.";
            } else if (userInput.contains("line chart")) {
                response = "For a line chart, select a column and click 'Line Chart'.";
            } else if (userInput.contains("custom graphics")) {
                response = "Let your creativity flow by drawing custom graphics on the right panel.";
            } else if (userInput.contains("goodbye") || userInput.contains("exit")) {
                response = "Goodbye! If you have more questions, feel free to return.";
            } else if (userInput.contains("thank you")) {
                response = "You're welcome! If you need further assistance, just ask.";
            } else if (userInput.contains("tell me more about data analysis")) {
                response = "Data analysis is the process of examining data to find trends, draw conclusions, and make decisions. In this app, you can explore and visualize data to gain insights.";
            } else if (userInput.contains("what's the purpose of custom graphics?")) {
                response = "Custom graphics allow you to illustrate and highlight specific data points or information. It's a creative way to add context to your analysis.";
            } else if (userInput.contains("can I save the charts I create?")) {
                response = "Currently, the app doesn't support saving charts, but you can take screenshots and save them manually.";
            } else if (userInput.contains("is there a limit to the data size I can load?")) {
                response = "The app's data loading depends on your computer's memory. Larger datasets may require more memory.";
            } else if (userInput.contains("are there keyboard shortcuts?")) {
                response = "Keyboard shortcuts are not available yet, but it's a great idea for a future update.";
            } else if (userInput.contains("How can I report issues or suggest improvements?")) {
                response = "You can provide feedback and report issues via the app's 'Help' menu. Your input is valuable!";
            } else if (userInput.contains("Can I customize the colors of the charts?")) {
                response = "Currently, chart customization is limited, but future updates may include this feature.";
            } else if (userInput.contains("tell me about the app's system requirements")) {
                response = "The app requires a Java Runtime Environment (JRE) to run. Please ensure you have JRE installed.";
            } else if (userInput.contains("what is the app's data format requirement for CSV files?")) {
                response = "CSV files should have comma-separated values and the first row as column headers for proper loading.";
            } else if (userInput.contains("Is there a maximum number of columns or rows supported?")) {
                response = "The app doesn't impose strict limits, but very large datasets may lead to performance issues.";
            } else if (userInput.contains("how often are updates released?")) {
                response = "Updates are typically released every few months, incorporating user feedback and improvements.";
            } else if (userInput.contains("Tell me about the app's developer")) {
                response = "The app is developed by data science practitioner for data analysts.";
            } else if (userInput.contains("what is the app's purpose?")) {
                response = "The app is designed to help data analysts explore and visualize data.";
            } else if (userInput.contains("what is the app's name?")) {
                response = "The app's name is DataGuru.";
            } else {
                response = "I'm here to assist with the Data Analysis Application. Feel free to ask any questions you may have.";
            }
    
            return response;
        }
    }