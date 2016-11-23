package org.incha.ui;


import org.incha.core.JavaProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;

/**
 * Created by constanzafierro on 22-11-16.
 */
public class GitHubSettings extends JPanel {
    private JTextField url = new JTextField(20);
    private JButton select = new JButton("Select");
    private SourcesEditor sourcesEditor;
    private File path;
    private String remoteUrl;

    public GitHubSettings(JavaProject project){
        super(new BorderLayout());
        sourcesEditor = new SourcesEditor(project);
        //url
        final JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
        final JLabel jLabel = new JLabel("URL Project GitHub: ",JLabel.LEFT);
        north.add(jLabel,BorderLayout.WEST);
        north.add(url,BorderLayout.EAST);
        add(north, BorderLayout.NORTH);
        // dir
        final JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT));
        final JLabel dirSelect = new JLabel("Select Directory: ",JLabel.LEFT);
        final JLabel selected = new JLabel("", JLabel.LEFT);
        center.add(dirSelect,BorderLayout.WEST);
        center.add(selected);
        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File f = sourcesEditor.selectFile();
                if(f!=null) {
                    selected.setText(f.toString());
                    setDir(f);
                }
            }
        });
        center.add(select,BorderLayout.EAST);
        add(center, BorderLayout.CENTER);
    }
    public void setDir(File f){
        this.path = f;
    }
    public void handleOk() throws IOException {
        remoteUrl = url.getText();
        File localPath = null;
        try {
            localPath = File.createTempFile("GiHubProject", "", path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!localPath.delete()) {
            throw new IOException("Could not delete temporary file " + localPath);
        }
        // then clone
        //System.out.println("Cloning from " + remoteUrl + " to " + localPath);
        try{
            Git.cloneRepository()
                    .setURI(remoteUrl)
                    .setDirectory(localPath)
                    .call();
            sourcesEditor.addFileToProject(localPath);
            //System.out.println("Having repository: " + result.getRepository().getDirectory());
        }
        catch (org.eclipse.jgit.api.errors.JGitInternalException e){
            windowConnectionError();
        }
        // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!

    }

    private void windowConnectionError() {
        final JDialog dialog = new JDialog();
        JPanel panel = new JPanel(new BorderLayout());
        JLabel error = new JLabel("Connection error, please check internet", JLabel.CENTER);
        JButton ok = new JButton("Ok");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        panel.add(error, BorderLayout.CENTER);
        panel.add(ok, BorderLayout.EAST);
        dialog.setContentPane(panel);
        dialog.pack();
        dialog.setVisible(true);
    }

}