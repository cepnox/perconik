package conmark.builder;

import java.util.Collection;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import conmark.Activator;
import conmark.utils.FileParser;

public class ConMkBuilder extends IncrementalProjectBuilder {
	
	void checkResource(IResource resource, int syncBuild) {
		if (resource instanceof IFile && resource.getName().endsWith(".java")) {
			IFile file = (IFile) resource;
			deleteMarkers(file);
			
			try {
				if(file.isSynchronized(syncBuild)){
					Collection<FileParser.Entry> l = FileParser.parse(file.getContents(), file.getCharset());
					if(l != null){
						for(FileParser.Entry tmp : l){
							IMarker marker;
							if(".Err".equals(tmp.getMarkerId())){
								marker = file.createMarker(IMarker.PROBLEM);
							}else{
								marker = file.createMarker(Activator.MARKER_TYPE+tmp.getMarkerId());
							}
							marker.setAttribute(IMarker.SEVERITY, ".Err".equals(tmp.getMarkerId()) ? IMarker.SEVERITY_ERROR : IMarker.SEVERITY_INFO);
							marker.setAttribute(Activator.MARKER_ID, tmp.getId());
							marker.setAttribute(IMarker.MESSAGE, tmp.getMessage());
							marker.setAttribute(IMarker.LINE_NUMBER, tmp.getLine());							
						}
					}
				}
				//
			} catch (CoreException e) {
				Activator.getDefault().getLog().log( new Status(IStatus.ERROR, Activator.PLUGIN_ID, IStatus.OK, "Exception", e) );
				//e.printStackTrace();
			} 			
		}
	}
	
	private void deleteMarkers(IFile file) {
		try {
			file.deleteMarkers(Activator.MARKER_TYPE+".Err", false, IResource.DEPTH_ZERO);
			for(String t : new String[]{"Eq", "Lt", "Gt"}){
				for(String c : new String[]{"Blue", "Red", "Green"}){
					file.deleteMarkers(Activator.MARKER_TYPE+"."+t+c, false, IResource.DEPTH_ZERO);
				}
			}
			
		} catch (CoreException ce) {
			Activator.getDefault().getLog().log( new Status(IStatus.ERROR, Activator.PLUGIN_ID, IStatus.OK, "Exception", ce) );
		}
	}
	
	// build
	@Override
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
		if (kind == FULL_BUILD) {
			fullBuild(monitor);
		} else {
			IResourceDelta delta = getDelta(getProject());
			if (delta == null) {
				fullBuild(monitor);
			} else {
				incrementalBuild(delta, monitor);
			}
		}
		return null;
	}

	// full
	
	protected void fullBuild(final IProgressMonitor monitor) throws CoreException {
		try {
			getProject().accept(new ResourceVisitor());
		} catch (CoreException e) {
			Activator.getDefault().getLog().log( new Status(IStatus.ERROR, Activator.PLUGIN_ID, IStatus.OK, "Exception", e) );
		}
	}
	
	class ResourceVisitor implements IResourceVisitor {
		@Override
		public boolean visit(IResource resource) {
			checkResource(resource, FULL_BUILD);
			return true;
		}
	}
	
	// delta
	
	protected void incrementalBuild(IResourceDelta delta, IProgressMonitor monitor) throws CoreException {
		delta.accept(new DeltaVisitor());
	}
	
	class DeltaVisitor implements IResourceDeltaVisitor {
		@Override
		public boolean visit(IResourceDelta delta) throws CoreException {
			IResource resource = delta.getResource();
			switch (delta.getKind()) {
			case IResourceDelta.ADDED:
				checkResource(resource, INCREMENTAL_BUILD);
				break;
			case IResourceDelta.REMOVED:
				if(resource instanceof IFile){
					deleteMarkers( (IFile)resource );					
				}
				break;
			case IResourceDelta.CHANGED:
				checkResource(resource, INCREMENTAL_BUILD);
				break;
			}
			return true;
		}
	}	
}
