#!/usr/bin/env python

#  Copyright Contributors to the OpenCue Project
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.


from outline import Outline, cuerun, Layer
from outline.modules.shell import Shell

ol = Outline("my_job")
layer = Shell("my_layer", command=["ls", "-la"])
layer.set_env("REZ_CONFIG_FILE", "/home/rezconfig.py")
layer.set_env("REZ_CONFIG_FILE1", "/home/rezconfig.py1")
layer.set_arg("tags", "daf4e7b20669414fba0e86aff260d99f")
layer.set_arg("cores", 4.0)
layer.set_arg("threadable", True)
layer.set_limits(["limit1", "limit2"])
ol.add_layer(layer)

cuerun.launch(ol, range="1-1", pause=True, use_pycuerun=False)
