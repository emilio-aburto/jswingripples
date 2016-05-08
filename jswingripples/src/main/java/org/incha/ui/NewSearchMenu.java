package org.incha.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

public class NewSearchMenu {
	
	private JTextField searchedWords;
	private List<String> words;
	private JPanel searchpanel;
	//private Search search;   Clase que implementa la búsqueda
	
	/**
     * Default constructor. Se crea un panel de búsqueda para colocarlo en algún menú.
     */
	public NewSearchMenu() {
		searchpanel = new JPanel();
		words = new ArrayList<String>();
		searchedWords = new JTextField(15);	
        searchpanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        searchpanel.setLayout(new FlowLayout());      
        searchpanel.add(new JLabel("Search:"));
        searchpanel.add(searchedWords);    
        JButton searchbutton = new JButton("Go");
        searchbutton.addActionListener(new ActionListener()
        {     	
                @Override
                public void actionPerformed(final ActionEvent e) {                	
                    final String text = searchedWords.getText();                    
                    final String cleanedText = text.trim();                    
                    if (text != null && cleanedText.length() > 0) {
                    	words = getWordList(cleanedText);                    	
                        //search = new Search(words);  Se entregan las palabras a la clase que hará la búsqueda                                           	
                    }
                }
            });
        searchpanel.add(searchbutton);
	}
	
	public JPanel getSearchPanel() {
		return searchpanel;
	}
	
	public List<String> getWordList(String text) {
		words.clear();
		final StringBuffer word = new StringBuffer("");
		for (int i = 0; i < text.length(); i ++) {
    		if (text.charAt(i) == ';') {//Encuentra un separador de palabras
    			words.add(word.toString().trim());  //Se tiene una nueva palabra y se coloca en la lista
    			word.delete(0, word.length());  //Limpio StringBuffer para guardar otra palabra
    		}
    		else {
    			word.append(text.charAt(i));//Concateno un caracter a la palabra actual
    		}
    	}
		words.add(word.toString().trim());		
		return words;
	}

}
