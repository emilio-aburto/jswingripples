package org.incha.ui.export;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.incha.core.jswingripples.eig.JSwingRipplesEIG;
import org.incha.core.jswingripples.eig.JSwingRipplesEIGNode;
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
		JavaProjectsModel projectsModel = JavaProjectsModel.getInstance();
		List<JavaProject> projectsList = projectsModel.getProjects();
		for (JavaProject project : projectsList) {
			JSwingRipplesEIG eig = project.getCurrentStatistics().getEIG();
			for (int i = 0; i < eig.getAllNodes().length; i++){
				System.out.println("Node: " + eig.getAllNodes()[i].getFullName() + " Mark: " + eig.getAllNodes()[i].getMark());
			}
		}
	}

}
