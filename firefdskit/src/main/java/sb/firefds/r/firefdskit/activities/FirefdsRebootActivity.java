/*
 * Copyright (C) 2021 Shauli Bracha for Firefds Kit Project (Firefds@xda)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sb.firefds.r.firefdskit.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import static sb.firefds.r.firefdskit.rebootactions.RebootActionFactory.getRebootAction;
import static sb.firefds.r.firefdskit.utils.Constants.REBOOT_ACTION;

public class FirefdsRebootActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getRebootAction(getIntent().getStringExtra(REBOOT_ACTION))
                .ifPresent(rebootAction -> rebootAction.reboot(getApplicationContext()));
    }
}