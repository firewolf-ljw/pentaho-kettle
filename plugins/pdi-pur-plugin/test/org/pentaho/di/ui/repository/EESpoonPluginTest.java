/*!
 * Copyright 2010 - 2016 Pentaho Corporation.  All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.pentaho.di.ui.repository;

import org.junit.Before;
import org.junit.Test;
import org.pentaho.di.ui.spoon.SpoonLifecycleListener;
import org.pentaho.di.ui.spoon.SpoonPerspectiveManager;

import static org.mockito.Mockito.*;

public class EESpoonPluginTest {
  private static EESpoonPlugin eeSpoonPlugin;
  private static SpoonPerspectiveManager perspectiveManager;

  @Before
  public void setUp() {
    eeSpoonPlugin = mock( EESpoonPlugin.class );
    doCallRealMethod().when( eeSpoonPlugin ).updateSchedulePerspective( anyBoolean() );

    perspectiveManager = mock( SpoonPerspectiveManager.class );
    doReturn( perspectiveManager ).when( eeSpoonPlugin ).getPerspectiveManager();
  }

  @Test
  public void schedulePerspectiveIsShownWhenUserHasPermissionsForScheduling() {
    eeSpoonPlugin.updateSchedulePerspective( true );
    verify( perspectiveManager ).showPerspective( "schedulerPerspective" );
  }

  @Test
  public void schedulePerspectiveIsHiddenWhenUserHasNoPermissionsForScheduling() {
    eeSpoonPlugin.updateSchedulePerspective( false );
    verify( perspectiveManager ).hidePerspective( "schedulerPerspective" );
  }

  @Test
  public void schedulePerspectiveIsHiddenWhenUserDisconnectsFromRepository() {
    doCallRealMethod().when( eeSpoonPlugin ).onEvent( SpoonLifecycleListener.SpoonLifeCycleEvent.REPOSITORY_DISCONNECTED );

    eeSpoonPlugin.onEvent( SpoonLifecycleListener.SpoonLifeCycleEvent.REPOSITORY_DISCONNECTED );

    verify( perspectiveManager ).hidePerspective( "schedulerPerspective" );
  }
}
