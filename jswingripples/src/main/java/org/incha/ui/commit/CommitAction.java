package org.incha.ui.commit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.incha.core.JavaProject;
import org.incha.ui.JSwingRipplesApplication;
import org.incha.ui.stats.StartAnalysisDialog;

public class CommitAction implements ActionListener {
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		final JSwingRipplesApplication app = JSwingRipplesApplication.getInstance();
        final CommitDialog dialog = new CommitDialog(app, this);
        dialog.pack();
        dialog.setLocationRelativeTo(app);
        dialog.setVisible(true);
	}

	public void commit(JavaProject project, String phase, String issue) {
		// TODO Auto-generated method stub
		System.out.println("NEW COMMIT");
		System.out.println("NAME: JSwingRipples commit");
		System.out.println("MESSAGE: Project: " + project.getName() + " Phase: " + phase + " Issue: " + issue);
	}

}
