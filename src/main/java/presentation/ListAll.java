package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * The ListAll class generates a table to display a list of items dynamically.
 *
 * @param <T> The type of items to be displayed in the table.
 */
public class ListAll<T> {

    /**
     * Generates a table to display a list of items.
     *
     * @param items The list of items to be displayed in the table.
     */
    public void generateTable(ArrayList<T> items) {
        if (items.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No items to display.");
            return;
        }

        // Obtain the class of the items
        Class<?> clazz = items.get(0).getClass();
        JFrame frame = createFrame(clazz);
        DefaultTableModel model = createTableModel(clazz);
        JTable table = createTable(model);

        // Add columns to the table model based on the class fields
        addColumnsToModel(model, clazz);

        // Populate the table with data from the list of items
        populateTable(items, model, clazz);

        // Add the table to a scroll pane and display the frame
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(700, 300);
    }

    /**
     * Creates the frame to hold the table.
     *
     * @param clazz The class of the items.
     * @return The created JFrame.
     */
    private JFrame createFrame(Class<?> clazz) {
        JFrame frame = new JFrame("List All " + clazz.getSimpleName());
        frame.getContentPane().setBackground(new Color(255, 208, 208));
        return frame;
    }

    /**
     * Creates the table model.
     *
     * @param clazz The class of the items.
     * @return The created DefaultTableModel.
     */
    private DefaultTableModel createTableModel(Class<?> clazz) {
        return new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    /**
     * Creates the table.
     *
     * @param model The table model to be used.
     * @return The created JTable.
     */
    private JTable createTable(DefaultTableModel model) {
        JTable table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setBackground(new Color(255, 208, 208));
        table.getTableHeader().setFont(new Font("TT Octosquares Trl", Font.PLAIN, 14));

        // Customize table header rendering
        table.getTableHeader().setDefaultRenderer(new CustomTableHeaderRenderer());

        return table;
    }

    /**
     * Adds columns to the table model based on the class fields.
     *
     * @param model The table model to which columns will be added.
     * @param clazz The class of the items.
     */
    private void addColumnsToModel(DefaultTableModel model, Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            model.addColumn(field.getName());
        }
    }

    /**
     * Populates the table with data from the list of items.
     *
     * @param items The list of items.
     * @param model The table model.
     * @param clazz The class of the items.
     */
    private void populateTable(ArrayList<T> items, DefaultTableModel model, Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (T item : items) {
            Object[] rowData = new Object[fields.length];
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                try {
                    rowData[i] = fields[i].get(item);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            model.addRow(rowData);
        }
    }

    /**
     * Custom table header renderer to customize header appearance.
     */
    static class CustomTableHeaderRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JTableHeader header = table.getTableHeader();
            setBackground(new Color(202, 135, 135));
            setForeground(new Color(240, 235, 227));

            setFont(new Font("TT Octosquares Trl", Font.PLAIN, 14));
            setHorizontalAlignment(SwingConstants.CENTER);
            setText(value.toString());
            return this;
        }
    }

}
