package org.incha.ui.export;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import org.incha.ui.JSwingRipplesApplication;

public class ExportMarksDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5535469793452851725L;
	
	private final JButton exportMarksButton = new JButton ("Export Marks");
	private JavaProject project;
	final Window ownerWindow;
	final JComboBox<String> projects;
	
	public ExportMarksDialog(final Window owner){
		super(owner);
		ownerWindow = owner;
		setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 5));
		
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
		
		final JPanel center = new JPanel(new FlowLayout(FlowLayout.LEADING));
        final JPanel selectProject = createCenterPanel();
		//final JPanel selectProject = new JPanel();
        center.add(selectProject);

        getContentPane().add(center, BorderLayout.CENTER);
        final JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
        exportMarksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                doOk();
            }
        });
        south.add(exportMarksButton);
        
        final JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                doCancel();
            }
        });
        south.add(cancel);
        getContentPane().add(south, BorderLayout.SOUTH);
        
        
        projectChanged();
	}

	protected void doCancel() {
		dispose();
		
	}

	protected void doOk() {
		ExportMarksAction.printProjectMarks(project);
		dispose();
	}

	private JPanel createCenterPanel() {
		final JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED),
                new EmptyBorder(3, 3, 3, 3)));
        panel.add(new JLabel("Java project:"));
        panel.add(projects);

        projects.setEditable(false);

        return panel;
	}

	protected void projectChanged() {
		project = JavaProjectsModel.getInstance().getProject((String) projects.getSelectedItem());
		
	}
	

}
