package Client.ui;

import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;

public class ComboSuggestion<E> extends JComboBox<E> {
	

	public ComboSuggestion() {
//		setUI(new ComboSuggestion());
		setUI(new ComboSuggestionUI());
	}
}
