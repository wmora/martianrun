/*
 * Copyright (c) 2015. William Mora
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gamestudio24.martianrun;

import com.badlogic.gdx.Game;
import com.gamestudio24.martianrun.screens.GameScreen;
import com.gamestudio24.martianrun.utils.AssetsManager;
import com.gamestudio24.martianrun.utils.AudioUtils;
import com.gamestudio24.martianrun.utils.GameEventListener;
import com.gamestudio24.martianrun.utils.GameManager;

public class MartianRun extends Game {

    public MartianRun(GameEventListener listener) {
        GameManager.getInstance().setGameEventListener(listener);
    }

    @Override
    public void create() {
        AssetsManager.loadAssets();
        setScreen(new GameScreen());
    }

    @Override
    public void dispose() {
        super.dispose();
        AudioUtils.dispose();
        AssetsManager.dispose();
    }
}
