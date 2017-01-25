package org.incha.ui.export;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.incha.core.jswingripples.eig.JSwingRipplesEIG;
import org.incha.core.jswingripples.eig.JSwingRipplesEIGNode;
import org.incha.ui.JSwingRipplesApplication;
import org.incha.core.JavaProject;
import org.incha.core.JavaProjectsModel;
import java.util.List;

public class ExportMarksAction implements ActionListener {
	
	public ExportMarksAction() {
		super();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//printMarks();
		final JSwingRipplesApplication app = JSwingRipplesApplication.getInstance();
		final ExportMarksDialog dialog = new ExportMarksDialog(app);
		dialog.pack();
        dialog.setLocationRelativeTo(app);
        dialog.setVisible(true);
	}

	private void printMarks() {
		JavaProjectsModel projectsModel = JavaProjectsModel.getInstance();
		List<JavaProject> projectsList = projectsModel.getProjects();
		for (JavaProject project : projectsList) {
			JSwingRipplesEIG eig = project.getCurrentStatistics().getEIG();
			for (int i = 0; i < eig.getAllNodes().length; i++){
				System.out.println("Node: " + eig.getAllNodes()[i].getFullName() + " Mark: " + eig.getAllNodes()[i].getMark());
			}
		}
	}

	public static void printProjectMarks(JavaProject project, String issue) {
		JSwingRipplesEIG eig = project.getCurrentStatistics().getEIG();
		for (int i = 0; i < eig.getAllNodes().length; i++){
			//System.out.println("Node: " + eig.getAllNodes()[i].getFullName() + " Mark: " + eig.getAllNodes()[i].getMark());
			System.out.println(buildComment(eig, issue, i));
		}
	}

	private static String buildComment(JSwingRipplesEIG eig, String issue, int i) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder("//@JRipples: Issue ");
		sb.append(issue);
		sb.append(" , ");
		sb.append(eig.getAllNodes()[i].getFullName());
		sb.append(": ");
		sb.append(eig.getAllNodes()[i].getMark());
		return sb.toString();
	}

}
