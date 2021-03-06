/**
 * CurrentProject.java
 * Created on 13.02.2003, 13:16:52 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 *
 */
package net.sf.memoranda;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Vector;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.ui.AppFrame;
import net.sf.memoranda.util.Context;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Storage;

/**
 *
 */
/*$Id: CurrentProject.java,v 1.6 2005/12/01 08:12:26 alexeya Exp $*/
public class CurrentProject {

	private static ContactList _contactlist = null;
    private static Project _project = null;
    private static TaskList _tasklist = null;
    private static DefectList _defectlist = null;
    private static NoteList _notelist = null;
    private static TimeLog _timelog = null;
    private static TaskPlanningLog _taskplanninglog = null;
    private static ResourcesList _resources = null;
    private static Vector projectListeners = new Vector();

        
    static {
        String prjId = (String) Context.get("LAST_OPENED_PROJECT_ID");
        if (prjId == null) {
            prjId = "__default";
            Context.put("LAST_OPENED_PROJECT_ID", prjId);
        }
        
        _project = ProjectManager.getProject(prjId);
		
        // Check if project fails to load
		if (_project == null) {
			_project = ProjectManager.getProject("__default");
			
			if (_project == null) 
				_project = (Project) ProjectManager.getActiveProjects().get(0);
			
			if(_project == null)
				_project = (Project) ProjectManager.createProject("Default", "", new CalendarDate(), null);

			Context.put("LAST_OPENED_PROJECT_ID", _project.getID());
		}
		
		_contactlist = CurrentStorage.get().openContactList(_project);
        _tasklist = CurrentStorage.get().openTaskList(_project);
        _defectlist = CurrentStorage.get().openDefectList(_project);
        _notelist = CurrentStorage.get().openNoteList(_project);
        _timelog = CurrentStorage.get().openTimeLog(_project);
        _taskplanninglog = CurrentStorage.get().openTaskPlanningLog(_project);
        _resources = CurrentStorage.get().openResourcesList(_project);
        AppFrame.addExitListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save();                                               
            }
        });
    }
        

    public static Project get() {
        return _project;
    }

    public static ContactList getContactList() {
    	return _contactlist;
    }
    
    public static TaskList getTaskList() {
        return _tasklist;
    }
    
    public static DefectList getDefectList() {
    	return _defectlist;
    }

    public static NoteList getNoteList() {
        return _notelist;
    }
    
    public static TimeLog getTimeLog() {
    	return _timelog;
    }
    
    public static TaskPlanningLog getTaskPlanningLog() {
    	return _taskplanninglog;
    }
    
    public static ResourcesList getResourcesList() {
        return _resources;
    }

    public static void set(Project project) {
        if (project.getID().equals(_project.getID())) return;
        ContactList newcontactlist = CurrentStorage.get().openContactList(project);
        TaskList newtasklist = CurrentStorage.get().openTaskList(project);
        DefectList newdefectlist = CurrentStorage.get().openDefectList(project);
        NoteList newnotelist = CurrentStorage.get().openNoteList(project);
        TimeLog newtimelog = CurrentStorage.get().openTimeLog(project);
        TaskPlanningLog newtaskplanninglog = CurrentStorage.get().openTaskPlanningLog(project);
        ResourcesList newresources = CurrentStorage.get().openResourcesList(project);
        notifyListenersBefore(project, newnotelist, newtasklist, newdefectlist, newresources);
        _project = project;
        _contactlist = newcontactlist;
        _tasklist = newtasklist;
        _defectlist = newdefectlist;
        _notelist = newnotelist;
        _timelog = newtimelog;
        _taskplanninglog = newtaskplanninglog;
        _resources = newresources;
        notifyListenersAfter();
        Context.put("LAST_OPENED_PROJECT_ID", project.getID());
    }

    public static void addProjectListener(ProjectListener pl) {
        projectListeners.add(pl);
    }

    public static Collection getChangeListeners() {
        return projectListeners;
    }

    private static void notifyListenersBefore(Project project, NoteList nl, TaskList tl, DefectList dl, ResourcesList rl) {
        for (int i = 0; i < projectListeners.size(); i++) {
            ((ProjectListener)projectListeners.get(i)).projectChange(project, nl, tl, dl, rl);
            /*DEBUGSystem.out.println(projectListeners.get(i));*/
        }
    }
    
    private static void notifyListenersAfter() {
        for (int i = 0; i < projectListeners.size(); i++) {
            ((ProjectListener)projectListeners.get(i)).projectWasChanged();            
        }
    }

    public static void save() {
        Storage storage = CurrentStorage.get();

        storage.storeNoteList(_notelist, _project);
        storage.storeDefectList(_defectlist, _project);
        storage.storeContactList(_contactlist, _project);
        storage.storeTaskList(_tasklist, _project); 
        storage.storeTimeLog(_timelog, _project);
        storage.storeTaskPlanningLog(_taskplanninglog, _project);
        storage.storeResourcesList(_resources, _project);
        storage.storeProjectManager();
    }
    
    public static void free() {
    	_contactlist = null;
        _project = null;
        _tasklist = null;
        _defectlist = null;
        _notelist = null;
        _resources = null;
    }
}
