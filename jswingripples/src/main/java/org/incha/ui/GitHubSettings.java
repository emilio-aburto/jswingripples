package org.incha.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.kohsuke.github.*;

import org.incha.core.JavaProject;
import org.incha.core.ModelSerializer;

/**
 * Created by Amoreno on 11/23/16.
 */
public class GitHubSettings extends JPanel{

    private static final long serialVersionUID = 3049936845151322175L;
    private final JTextField url;
    private final JLabel crntRepo;   
    private final JButton connect;    
    private final JavaProject pjct;
    private final JButton retrieveIssuesBtn;
    private final static String WRONG_FORMAT = "wrong_format";
    private  GitHub github = null;
   
    /**
     * Creates JPanel for project's GitHub settings
     * @param project
     * @return
     */    
    public GitHubSettings(JavaProject project){
        super(new BorderLayout());
        this.pjct = project;
        url = new JTextField(20);
        connect = new JButton("Connect");
        crntRepo = new JLabel("Current Repository: ");
        crntRepo.setText(crntRepo.getText() + obtainCurrentRepo(pjct));
        retrieveIssuesBtn = new JButton("Retrieve open issues");

        configureLayout();        
        configListeners();
    }
    
    private void configureLayout(){
        JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
        north.add(new JLabel("GitHub URL repository: "), BorderLayout.WEST);
        north.add(url, BorderLayout.EAST);
        
        north.add(connect, BorderLayout.EAST);
        
        center.add(crntRepo,BorderLayout.WEST);
        center.add(retrieveIssuesBtn,BorderLayout.WEST);
        
        this.add(north, BorderLayout.NORTH);
        this.add(center, BorderLayout.CENTER);
    }
    
    private void configListeners(){
        
        performConnection();
        
        /**
         * Listener to the button that connect the App with GitHub
         */
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {        
                try {                    
                    String repo = formatURLRepo(url.getText());
                    url.setText("");                    
                    if (repo.compareTo(WRONG_FORMAT) == 0){
                    	JOptionPane.showMessageDialog(null, "Incorrect URL format. Accepted formats are:\n"
                    			+ "https://github.com/*.git\n"
                    			+ "https://github.com/*",
                    			"Format error", JOptionPane.ERROR_MESSAGE);
                    	return;
                    }
                    /* Getting the repository is just used to verify its availability,
                     * since there is no another way to do this */
                    github.getRepository(repo);                    
                    pjct.getGHRepo().replaceRepository(repo);                    
                    crntRepo.setText("Current Repository: " + repo);
                } catch (IOException e1) {
                	JOptionPane.showMessageDialog(null, "Check your internet connection or\n"
                			+ "if the requested repository is public",
                			"Connection error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e2){
                	JOptionPane.showMessageDialog(null, "Please check existence of the repository",
                			"Unknown exception", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        });
        
        /**
         * Listener to the button that retrieve the issues list from GitHub
         */
        retrieveIssuesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //Retrieve the current repository
                    String repository = pjct.getGHRepo().getCurrentRepository();
                    //Retrieve the open issues as a list from reporitory                    
                    List<GHIssue> openIssuesList = new LinkedList<GHIssue>(github.getRepository(repository).getIssues(GHIssueState.OPEN));                    
                    ModelSerializer.generate(openIssuesList, pjct.getName());
                } catch (IOException e1) {
                	JOptionPane.showMessageDialog(null, "Check your internet connection or\n"
                			+ "if the requested repository is public",
                			"Connection error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e2){
                	JOptionPane.showMessageDialog(null, "Please check existence of the repository",
                			"Unknown exception", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
    /**
     * This method looks for the project's current repository.
     * If there is no, return empty String.
     * @param project
     * @return
     */
    private String obtainCurrentRepo(JavaProject project){
    	return project.getGHRepo().getCurrentRepository();
    }
    
    /**
     * Formats an url to make it compatible with kohsuke GitHub API. 
     * @param urlRepo
     * @return
     */
    protected static String formatURLRepo (String urlRepo){
    	if(urlRepo.startsWith("https://github.com/")){
    		if (urlRepo.endsWith(".git")){
    			return urlRepo.split("https://github.com/")[1].split(".git")[0];
    		}
    		return urlRepo.split("https://github.com/")[1];
    	} 
    	return WRONG_FORMAT;
    			 
    }
    
    /**
     * Method to stablish the connection between the App and GitHub
     */
    public void performConnection() {
        try {
            github = GitHub.connectAnonymously();
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(null, "Check your internet connection or\n"
                			+ "if the requested repository is public",
                			"Connection error", JOptionPane.ERROR_MESSAGE);
        }
    }
}