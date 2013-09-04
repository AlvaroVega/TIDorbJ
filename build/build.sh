#
# MORFEO Project
# http://www.morfeo-project.org
#
# Component: TIDIdlc
# Programming Language: Java
#
# File: $Source$
# Version: $Revision: 21 $
# Date: $Date: 2006-07-14 14:08:38 +0200 (Fri, 14 Jul 2006) $
# Last modified by: $Author: iredondo $
#
# (C) Copyright 2004 Telefónica Investigación y Desarrollo
#     S.A.Unipersonal (Telefónica I+D)
#
# Info about members and contributors of the MORFEO project
# is available at:
#
#   http://www.morfeo-project.org/TIDIdlc/CREDITS
#
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; either version 2 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
#
# If you want to use this software an plan to distribute a
# proprietary application in any way, and you are not licensing and
# distributing your source code under GPL, you probably need to
# purchase a commercial license of the product.  More info about
# licensing options is available at:
#
#   http://www.morfeo-project.org/TIDIdlc/Licensing
#
#$ANT_HOME/bin/ant -f ../build.xml -Dmorfeo.dependencies.home=$MORFEO_DEPENDENCIES -verbose $*
set +u
OLD_DIR=$PWD

cd ../../MORFEO_DEPENDENCIES
DEFAULT_MORFEO_DEPENDENCIES=$PWD
cd $OLD_DIR

if [ -z "$MORFEO_DEPENDENCIES" ]; then
         echo Configurando la variable MORFEO_DEPENDENCIIES=$DEFAULT_MORFEO_DEPENDENCIES
	 echo Si el valor de esta variable no es correcto ejecute el script
	 echo $MORFEO_DEPENDENCIES/bin/set_env.sh
     cd $DEFAULT_MORFEO_DEPENDENCIES/bin
	echo $DEFAULT_MORFEO_DEPENDENCIES
	. ./set_env.sh
	cd $OLD_DIR
fi
$MORFEO_DEPENDENCIES/build/build.sh $*
