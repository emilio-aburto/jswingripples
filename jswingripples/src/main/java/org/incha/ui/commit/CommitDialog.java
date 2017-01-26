package org.incha.ui.commit;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import org.incha.core.JavaProject;
import org.incha.core.JavaProjectsModel;
import org.incha.ui.IssuesReader;
import org.incha.ui.JSwingRipplesApplication;

public class CommitDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 270995731241561531L;
	
	private final JButton commitButton = new JButton("Commit");
	private final JButton cancelButton = new JButton("Cancel");
	private final CommitAction commitCallback;
	private JavaProject project;
	final Window ownerWindow;
	private String phase;
	private String issue;
	final JComboBox<String> projects;
	final JComboBox<String> phases;
	final JComboBox<String> issues;
	private final String[] phaseTypes = {"Concept Location", "Impact Analysis"};
	

	public CommitDialog(final Window owner, CommitAction callback) {
		// TODO Auto-generated constructor stub
		super(owner);
		ownerWindow = owner;
		commitCallback = callback;
		setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 5));
		
		//create projects combobox.
        final List<JavaProject> prg = JavaProjectsModel.getInstance().getProjects();
        final String[] prgArray = new String[prg.size()];
        for (int i = 0; i < prgArray.length; i++) {
            prgArray[i] = prg.get(i).getName();
        }
        projects = new JComboBox<>(new DefaultComboBoxModel<>(prgArray));
        projects.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                projectChanged();
            }
        });
        
        phases = new JComboBox<>(new DefaultComboBoxModel<>(phaseTypes));
        phases.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(final ActionEvent e) {
        		phaseChanged();
        	}
        });
        
        issues = new JComboBox<>();
        issues.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(final ActionEvent e) {
        		issueChanged();
        	}
        });
        
        final JPanel center = new JPanel(new FlowLayout(FlowLayout.LEADING));
        final JPanel projectPhaseAndIssue = createCenterPanel();
        center.add(projectPhaseAndIssue);
        
        getContentPane().add(center, BorderLayout.CENTER);

        //south pane
        //commitButton.setEnabled(false);
        final JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
        commitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                commitCallback.commit(project, phase, issue);
            }
        });
        south.add(commitButton);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                doCancel();
            }
        });
        south.add(cancelButton);
        getContentPane().add(south, BorderLayout.SOUTH);
        
        projectChanged();
        
	}


	protected void doCancel() {
		// TODO Auto-generated method stub
		dispose();
	}


	protected void commit() {
		// TODO Auto-generated method stub
		commitCallback.commit(project, phase, issue);
		dispose();
	}


	private JPanel createCenterPanel() {
		// TODO Auto-generated method stub
		final JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED),
                new EmptyBorder(3, 3, 3, 3)));
        panel.add(new JLabel("Java project:"));
        panel.add(projects);
        panel.add(new JLabel("Phase:"));
        panel.add(phases);
        panel.add(new JLabel("Issue:"));
        panel.add(issues);
        phases.setEditable(false);
        projects.setEditable(false);
        issues.setEditable(false);
        return panel;
	}


	protected void issueChanged() {
		// TODO Auto-generated method stub
		issue = issues.getSelectedItem().toString();
	}


	protected void phaseChanged() {
		// TODO Auto-generated method stub
		phase = phases.getSelectedItem().toString();
	}


	protected void projectChanged() {
		// TODO Auto-generated method stub
		project = JavaProjectsModel.getInstance().getProject((String) projects.getSelectedItem());
		if (project != null) {
			IssuesReader issuesReader = new IssuesReader(JSwingRipplesApplication.getHome()+ File.separator+project.getName()+".xml");
			issuesReader.load();
			String[] issuesArray = new String[issuesReader.loadData().length];
			for (int i = 0; i < issuesArray.length; i++) {
				issuesArray[i] = issuesReader.loadData()[i][0].toString() + " - " + issuesReader.loadData()[i][1].toString();
			}
			issues.setModel(new DefaultComboBoxModel<>(issuesArray));
			issueChanged();
		}
	}

}
