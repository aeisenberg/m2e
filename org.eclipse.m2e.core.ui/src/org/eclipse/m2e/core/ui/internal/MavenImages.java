/*******************************************************************************
 * Copyright (c) 2008-2010 Sonatype, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Sonatype, Inc. - initial API and implementation
 *******************************************************************************/

package org.eclipse.m2e.core.ui.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;


/**
 * @author Eugene Kuleshov
 */
public class MavenImages {
  private static final Logger log = LoggerFactory.getLogger(MavenImages.class);

  // descriptors
  public static final ImageDescriptor M2 = createDescriptor("m2.gif"); //$NON-NLS-1$

  public static final ImageDescriptor DEBUG = createDescriptor("debug.gif"); //$NON-NLS-1$

  public static final ImageDescriptor ADD_INDEX = createDescriptor("add_index.gif"); //$NON-NLS-1$

  public static final ImageDescriptor NEW_POM = createDescriptor("new_m2_pom.gif"); //$NON-NLS-1$

  public static final ImageDescriptor REFRESH = createDescriptor("refresh.gif"); //$NON-NLS-1$

  public static final ImageDescriptor UPD_INDEX = createDescriptor("update_index.gif"); //$NON-NLS-1$

  public static final ImageDescriptor REBUILD_INDEX = createDescriptor("rebuild_index.gif"); //$NON-NLS-1$

  public static final ImageDescriptor POM = createDescriptor("pom_obj.gif"); //$NON-NLS-1$

  public static final ImageDescriptor SHOW_CONSOLE_ERR = createDescriptor("stderr.gif"); //$NON-NLS-1$

  public static final ImageDescriptor SHOW_CONSOLE_OUT = createDescriptor("stdout.gif"); //$NON-NLS-1$

  public static final ImageDescriptor MAVEN_OVERLAY = createDescriptor("mlabel.gif"); //$NON-NLS-1$

  public static final ImageDescriptor EXPANDALL = createDescriptor("expandall.gif"); //$NON-NLS-1$

  public static final ImageDescriptor COLLAPSEALL = createDescriptor("collapseall.gif"); //$NON-NLS-1$

  public static final ImageDescriptor SUSPEND = createDescriptor("suspend.gif"); //$NON-NLS-1$

  public static final ImageDescriptor CLEAR = createDescriptor("clear.gif"); //$NON-NLS-1$

  // object images
  public static final String PATH_JAR = "jar_obj.gif"; //$NON-NLS-1$

  public static final String PATH_PROJECT = "project_obj.gif"; //$NON-NLS-1$ 

  public static final String PATH_LOCK = "lock_ovr.gif"; //$NON-NLS-1$

  public static final String PATH_VERSION = "jar_version.gif"; //$NON-NLS-1$

  public static final String PATH_VERSION_SRC = "jar_src_version.gif"; //$NON-NLS-1$

  public static final Image IMG_JAR = createImage(PATH_JAR);

  public static final Image IMG_VERSION = createImage(PATH_VERSION);

  public static final Image IMG_VERSION_SRC = createImage(PATH_VERSION_SRC);

  public static final Image IMG_LAUNCH_MAIN = createImage("main_tab.gif"); //$NON-NLS-1$

  public static final Image IMG_INDEX = createImage("maven_index.gif"); //$NON-NLS-1$

  public static final Image IMG_INDEXES = createImage("maven_indexes.gif"); //$NON-NLS-1$

  public static final Image IMG_PASSED = createImage("passed.png"); //$NON-NLS-1$

  public static final Image IMG_POM = POM.createImage();

  public static final Image IMG_ERROR = createImage("error_st_obj.gif"); //$NON-NLS-1$

  // wizard images
  public static final ImageDescriptor WIZ_IMPORT_WIZ = createDescriptor("import_project.png"); //$NON-NLS-1$

  public static final ImageDescriptor WIZ_NEW_PROJECT = createDescriptor("new_m2_project_wizard.gif"); //$NON-NLS-1$

  public static final String MVN_PROJECT = "project-mlabel.gif"; //$NON-NLS-1$

  static {

  }

  private static ImageDescriptor createDescriptor(String key) {
    try {
      ImageRegistry imageRegistry = getImageRegistry();
      if(imageRegistry != null) {
        ImageDescriptor imageDescriptor = imageRegistry.getDescriptor(key);
        if(imageDescriptor == null) {
          imageDescriptor = doCreateDescriptor(key);
          imageRegistry.put(key, imageDescriptor);
        }
        return imageDescriptor;
      }
    } catch(Exception ex) {
      log.error(key, ex);
    }
    return null;
  }

  private static Image createImage(String key) {
    createDescriptor(key);
    ImageRegistry imageRegistry = getImageRegistry();
    return imageRegistry == null ? null : imageRegistry.get(key);
  }

  private static ImageRegistry getImageRegistry() {
    M2EUIPluginActivator plugin = M2EUIPluginActivator.getDefault();
    return plugin == null ? null : plugin.getImageRegistry();
  }

  private static ImageDescriptor doCreateDescriptor(String image) {
    return AbstractUIPlugin.imageDescriptorFromPlugin(M2EUIPluginActivator.PLUGIN_ID, "icons/" + image); //$NON-NLS-1$
  }

  private static ImageDescriptor getOverlayImageDescriptor(String basekey, String overlaykey, int quadrant) {
    String key = basekey + overlaykey;
    try {
      ImageRegistry imageRegistry = getImageRegistry();
      if(imageRegistry != null) {
        ImageDescriptor imageDescriptor = imageRegistry.getDescriptor(key);
        if(imageDescriptor == null) {
          ImageDescriptor base = createDescriptor(basekey);
          ImageDescriptor overlay = createDescriptor(overlaykey);
          if(base == null || overlay == null) {
            log.error("cannot construct overlay image descriptor for " + basekey + " " + overlaykey); //$NON-NLS-1$ //$NON-NLS-2$
            return null;
          }
          imageDescriptor = createOverlayDescriptor(base, overlay, quadrant);
          imageRegistry.put(key, imageDescriptor);
        }
        return imageDescriptor;
      }
    } catch(Exception ex) {
      log.error(key, ex);
    }
    return null;
  }

  public static Image getOverlayImage(String base, String overlay, int quadrant) {
    getOverlayImageDescriptor(base, overlay, quadrant);
    ImageRegistry imageRegistry = getImageRegistry();
    return imageRegistry == null ? null : imageRegistry.get(base + overlay);
  }

  public static Image createOverlayImage(String key, Image base, ImageDescriptor overlay, int quadrant) {
    ImageRegistry registry = getImageRegistry();
    if(registry != null) {
      Image image = registry.get(key);
      if(image == null) {
        ImageDescriptor descriptor = registry.getDescriptor(key);
        if(descriptor == null) {
          registry.put(key, createOverlayDescriptor(base, overlay, quadrant));
        }
        image = registry.get(key);
      }
      return image;
    }
    return null;
  }

  private static ImageDescriptor createOverlayDescriptor(ImageDescriptor base, ImageDescriptor overlay, int quadrant) {
    return new DecorationOverlayIcon(base.createImage(), overlay, quadrant);
  }

  private static ImageDescriptor createOverlayDescriptor(Image base, ImageDescriptor overlay, int quadrant) {
    return new DecorationOverlayIcon(base, overlay, quadrant);
  }
}
