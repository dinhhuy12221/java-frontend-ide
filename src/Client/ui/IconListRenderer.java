package Client.ui;

import java.awt.Component;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;

class IconListRenderer extends DefaultListCellRenderer{ 
    private static final long serialVersionUID = 1L;
    private Map<Object, Icon> icons = null; 

    public IconListRenderer(Map<Object, Icon> icons){ 
        this.icons = icons; 
    } 

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index,boolean isSelected, boolean cellHasFocus)
    { 
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus); 

        // Get icon to use for the list item value 
        Icon icon = icons.get(value); 

        // if(!value.toString().equals(NONE_STR)){
        //     icon = icons.get(INFO_STR);
        // }

        // Set icon to display for value 
        label.setIcon(icon); 
        return label; 
    } 
}