package ui;

import dao.ReportDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateReportFrame extends JFrame {

    private JTextArea reportArea;
    private JButton generateButton;
    private JButton printButton;
    private JButton exportTextButton;
    private JButton exportPdfButton;
    private JButton backButton;

    public GenerateReportFrame() {
        UIHelper.setupFrame(this, "Generate Report");

        JPanel mainPanel = UIHelper.createMainPanel();
        JLabel header = UIHelper.createHeader("System Report Generator", UIHelper.PRIMARY_BLUE);
        JPanel wrapper = UIHelper.createFormWrapperPanel();
        JPanel card = UIHelper.createCardPanel(1050, 680);

        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.setPreferredSize(new Dimension(950, 520));

        generateButton = UIHelper.createButton("Generate Report", UIHelper.PRIMARY_BLUE);
        printButton = UIHelper.createButton("Print Report", UIHelper.GREEN);
        exportTextButton = UIHelper.createButton("Export TXT", UIHelper.GREEN);
        exportPdfButton = UIHelper.createButton("Export PDF", UIHelper.GREEN);
        backButton = UIHelper.createButton("Back", UIHelper.GRAY);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 15, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(generateButton);
        buttonPanel.add(printButton);
        buttonPanel.add(exportTextButton);
        buttonPanel.add(exportPdfButton);
        buttonPanel.add(backButton);

        card.setLayout(new BorderLayout(15, 15));
        card.add(scrollPane, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.SOUTH);

        wrapper.add(Box.createVerticalStrut(20));
        wrapper.add(card);
        wrapper.add(Box.createVerticalGlue());

        generateButton.addActionListener(new GenerateButtonListener());
        printButton.addActionListener(new PrintButtonListener());
        exportTextButton.addActionListener(new ExportTextButtonListener());
        exportPdfButton.addActionListener(new ExportPdfButtonListener());
        backButton.addActionListener(new BackButtonListener());

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(wrapper, BorderLayout.CENTER);

        UIHelper.finalizeFrame(this, mainPanel);
    }

    private void generateReport() {
        ReportDAO dao = new ReportDAO();
        String report = dao.generateFullSystemReport();
        reportArea.setText(report);
    }

    private void exportTextReport() {
        if (reportArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please generate the report first.");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Report as TXT");
        fileChooser.setSelectedFile(new File("swym_nation_report.txt"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File fileToSave = fileChooser.getSelectedFile();

        try (FileWriter writer = new FileWriter(fileToSave)) {
            writer.write(reportArea.getText());
            JOptionPane.showMessageDialog(this, "Text report exported successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Could not export text report.");
        }
    }

    private void exportPdfReport() {
        if (reportArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please generate the report first.");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Report as PDF");
        fileChooser.setSelectedFile(new File("swym_nation_report.pdf"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File fileToSave = fileChooser.getSelectedFile();

        try {
            PDFExportHelper.exportTextToPDF(reportArea.getText(), fileToSave);
            JOptionPane.showMessageDialog(this, "PDF report exported successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Could not export PDF report.");
        }
    }

    private class GenerateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            generateReport();
        }
    }

    private class PrintButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (reportArea.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(GenerateReportFrame.this, "Please generate the report first.");
                return;
            }

            try {
                reportArea.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(GenerateReportFrame.this, "Could not print report.");
            }
        }
    }

    private class ExportTextButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            exportTextReport();
        }
    }

    private class ExportPdfButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            exportPdfReport();
        }
    }

    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new AdminDashboardFrame();
            dispose();
        }
    }
}