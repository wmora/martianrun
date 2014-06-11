package com.gamestudio24.cityescape.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.gamestudio24.cityescape.actors.*;
import com.gamestudio24.cityescape.actors.menu.*;
import com.gamestudio24.cityescape.enums.Difficulty;
import com.gamestudio24.cityescape.enums.GameState;
import com.gamestudio24.cityescape.utils.*;

public class GameStage extends Stage implements ContactListener {

    private static final int VIEWPORT_WIDTH = Constants.APP_WIDTH;
    private static final int VIEWPORT_HEIGHT = Constants.APP_HEIGHT;

    private World world;
    private Ground ground;
    private Runner runner;

    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private OrthographicCamera camera;

    private Rectangle screenLeftSide;
    private Rectangle screenRightSide;

    private SoundButton soundButton;
    private MusicButton musicButton;
    private PauseButton pauseButton;
    private StartButton startButton;
    private LeaderboardButton leaderboardButton;
    private AboutButton aboutButton;
    private ShareButton shareButton;

    private Score score;
    private float totalTimePassed;

    private Vector3 touchPoint;

    public GameStage() {
        super(new ScalingViewport(Scaling.stretch, VIEWPORT_WIDTH, VIEWPORT_HEIGHT,
                new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)));
        setUpCamera();
        setUpStageBase();
        setUpGameLabel();
        setUpMainMenu();
        setUpTouchControlAreas();
        Gdx.input.setInputProcessor(this);
        AudioUtils.getInstance().init();
        onGameOver();
    }

    private void setUpStageBase() {
        setUpWorld();
        setUpFixedMenu();
    }

    private void setUpGameLabel() {
        Rectangle gameLabelBounds = new Rectangle(0, getCamera().viewportHeight * 7 / 8, getCamera().viewportWidth,
                getCamera().viewportHeight / 4);
        addActor(new GameLabel(gameLabelBounds));
    }

    private void setUpAboutText() {
        Rectangle gameLabelBounds = new Rectangle(0, getCamera().viewportHeight * 5 / 8, getCamera().viewportWidth,
                getCamera().viewportHeight / 4);
        addActor(new AboutLabel(gameLabelBounds));
    }

    /**
     * These menu buttons are always displayed
     */
    private void setUpFixedMenu() {
        setUpSound();
        setUpMusic();
        setUpScore();
    }

    private void setUpSound() {
        Rectangle soundButtonBounds = new Rectangle(getCamera().viewportWidth / 64, getCamera().viewportHeight * 13 / 20,
                getCamera().viewportHeight / 10, getCamera().viewportHeight / 10);
        soundButton = new SoundButton(soundButtonBounds);
        addActor(soundButton);
    }

    private void setUpMusic() {
        Rectangle musicButtonBounds = new Rectangle(getCamera().viewportWidth / 64, getCamera().viewportHeight * 4 / 5,
                getCamera().viewportHeight / 10, getCamera().viewportHeight / 10);
        musicButton = new MusicButton(musicButtonBounds);
        addActor(musicButton);
    }

    private void setUpScore() {
        Rectangle scoreBounds = new Rectangle(getCamera().viewportWidth * 47 / 64, getCamera().viewportHeight * 57 / 64,
                getCamera().viewportWidth / 4, getCamera().viewportHeight / 8);
        score = new Score(scoreBounds);
        addActor(score);
    }

    private void setUpPause() {
        Rectangle pauseButtonBounds = new Rectangle(getCamera().viewportWidth / 64, getCamera().viewportHeight * 1 / 2,
                getCamera().viewportHeight / 10, getCamera().viewportHeight / 10);
        pauseButton = new PauseButton(pauseButtonBounds, new GamePauseButtonListener());
        addActor(pauseButton);
    }

    /**
     * These menu buttons are only displayed when the game is over
     */
    private void setUpMainMenu() {
        setUpStart();
        setUpLeaderboard();
        setUpAbout();
        setUpShare();
    }

    private void setUpStart() {
        Rectangle startButtonBounds = new Rectangle(getCamera().viewportWidth * 3 / 16, getCamera().viewportHeight / 4,
                getCamera().viewportWidth / 4, getCamera().viewportWidth / 4);
        startButton = new StartButton(startButtonBounds, new GameStartButtonListener());
        addActor(startButton);
    }

    private void setUpLeaderboard() {
        Rectangle leaderboardButtonBounds = new Rectangle(getCamera().viewportWidth * 9 / 16,
                getCamera().viewportHeight / 4, getCamera().viewportWidth / 4, getCamera().viewportWidth / 4);
        leaderboardButton = new LeaderboardButton(leaderboardButtonBounds, new GameLeaderboardButtonListener());
        addActor(leaderboardButton);
    }

    private void setUpAbout() {
        Rectangle aboutButtonBounds = new Rectangle(getCamera().viewportWidth * 23 / 25,
                getCamera().viewportHeight * 13 / 20, getCamera().viewportHeight / 10, getCamera().viewportHeight / 10);
        aboutButton = new AboutButton(aboutButtonBounds, new GameAboutButtonListener());
        addActor(aboutButton);
    }

    private void setUpShare() {
        Rectangle shareButtonBounds = new Rectangle(getCamera().viewportWidth / 64, getCamera().viewportHeight / 2,
                getCamera().viewportHeight / 10, getCamera().viewportHeight / 10);
        shareButton = new ShareButton(shareButtonBounds, new GameShareButtonListener());
        addActor(shareButton);
    }

    private void setUpWorld() {
        world = WorldUtils.createWorld();
        world.setContactListener(this);
        setUpBackground();
        setUpGround();
    }

    private void setUpBackground() {
        addActor(new Background());
    }

    private void setUpGround() {
        ground = new Ground(WorldUtils.createGround(world));
        addActor(ground);
    }

    private void setUpCharacters() {
        setUpRunner();
        setUpPauseLabel();
        createEnemy();
    }

    private void setUpRunner() {
        if (runner != null) {
            runner.remove();
        }
        runner = new Runner(WorldUtils.createRunner(world));
        addActor(runner);
    }

    private void setUpCamera() {
        camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
    }

    private void setUpTouchControlAreas() {
        touchPoint = new Vector3();
        screenLeftSide = new Rectangle(0, 0, getCamera().viewportWidth / 2, getCamera().viewportHeight);
        screenRightSide = new Rectangle(getCamera().viewportWidth / 2, 0, getCamera().viewportWidth / 2,
                getCamera().viewportHeight);
    }

    private void setUpPauseLabel() {
        Rectangle pauseLabelBounds = new Rectangle(0, getCamera().viewportHeight * 7 / 8, getCamera().viewportWidth,
                getCamera().viewportHeight / 4);
        addActor(new PausedLabel(pauseLabelBounds));
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (GameManager.getInstance().getGameState() == GameState.PAUSED) return;

        if (GameManager.getInstance().getGameState() == GameState.RUNNING) {
            totalTimePassed += delta;
            updateDifficulty();
        }

        Array<Body> bodies = new Array<Body>(world.getBodyCount());
        world.getBodies(bodies);

        for (Body body : bodies) {
            update(body);
        }

        // Fixed timestep
        accumulator += delta;

        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

        //TODO: Implement interpolation

    }

    private void update(Body body) {
        if (!BodyUtils.bodyInBounds(body)) {
            if (BodyUtils.bodyIsEnemy(body) && !runner.isHit()) {
                createEnemy();
            }
            world.destroyBody(body);
        }
    }

    private void createEnemy() {
        Enemy enemy = new Enemy(WorldUtils.createEnemy(world));
        enemy.getUserData().setLinearVelocity(GameManager.getInstance().getDifficulty().getEnemyLinearVelocity());
        addActor(enemy);
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {

        // Need to get the actual coordinates
        translateScreenToWorldCoordinates(x, y);

        // If a menu control was touched ignore the rest
        if (menuControlTouched(touchPoint.x, touchPoint.y)) {
            return super.touchDown(x, y, pointer, button);
        }

        if (GameManager.getInstance().getGameState() != GameState.RUNNING) {
            return super.touchDown(x, y, pointer, button);
        }

        if (rightSideTouched(touchPoint.x, touchPoint.y)) {
            runner.jump();
        } else if (leftSideTouched(touchPoint.x, touchPoint.y)) {
            runner.dodge();
        }

        return super.touchDown(x, y, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if (GameManager.getInstance().getGameState() != GameState.RUNNING) {
            return super.touchUp(screenX, screenY, pointer, button);
        }

        if (runner.isDodging()) {
            runner.stopDodge();
        }

        return super.touchUp(screenX, screenY, pointer, button);
    }

    private boolean menuControlTouched(float x, float y) {
        boolean touched = false;

        switch (GameManager.getInstance().getGameState()) {
            case OVER:
                touched = startButton.getBounds().contains(x, y) || leaderboardButton.getBounds().contains(x, y)
                        || aboutButton.getBounds().contains(x, y);
                break;
            case RUNNING:
            case PAUSED:
                touched = pauseButton.getBounds().contains(x, y);
                break;
        }

        return touched || soundButton.getBounds().contains(x, y) || musicButton.getBounds().contains(x, y);
    }

    private boolean rightSideTouched(float x, float y) {
        return screenRightSide.contains(x, y);
    }

    private boolean leftSideTouched(float x, float y) {
        return screenLeftSide.contains(x, y);
    }

    /**
     * Helper function to get the actual coordinates in my world
     *
     * @param x
     * @param y
     */
    private void translateScreenToWorldCoordinates(int x, int y) {
        getCamera().unproject(touchPoint.set(x, y, 0));
    }

    @Override
    public void beginContact(Contact contact) {

        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        if ((BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsEnemy(b)) ||
                (BodyUtils.bodyIsEnemy(a) && BodyUtils.bodyIsRunner(b))) {
            if (runner.isHit()) {
                return;
            }
            runner.hit();
            displayAd();
            GameManager.getInstance().submitScore(score.getScore());
            onGameOver();
        } else if ((BodyUtils.bodyIsRunner(a) && BodyUtils.bodyIsGround(b)) ||
                (BodyUtils.bodyIsGround(a) && BodyUtils.bodyIsRunner(b))) {
            runner.landed();
        }

    }

    private void updateDifficulty() {

        if (GameManager.getInstance().isMaxDifficulty()) {
            return;
        }

        Difficulty currentDifficulty = GameManager.getInstance().getDifficulty();

        if (totalTimePassed > GameManager.getInstance().getDifficulty().getLevel() * 5) {

            int nextDifficulty = currentDifficulty.getLevel() + 1;
            String difficultyName = "DIFFICULTY_" + nextDifficulty;
            GameManager.getInstance().setDifficulty(Difficulty.valueOf(difficultyName));

            runner.onDifficultyChange(GameManager.getInstance().getDifficulty());
            score.setMultiplier(GameManager.getInstance().getDifficulty().getScoreMultiplier());

            displayAd();
        }

    }

    private void displayAd() {
        GameManager.getInstance().displayAd();
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private class GamePauseButtonListener implements PauseButton.PauseButtonListener {

        @Override
        public void onPause() {
            onGamePaused();
        }

        @Override
        public void onResume() {
            onGameResumed();
        }

    }

    private class GameStartButtonListener implements StartButton.StartButtonListener {

        @Override
        public void onStart() {
            clear();
            setUpStageBase();
            setUpCharacters();
            setUpPause();
            onGameResumed();
        }

    }

    private class GameLeaderboardButtonListener implements LeaderboardButton.LeaderboardButtonListener {

        @Override
        public void onLeaderboard() {
            GameManager.getInstance().displayLeaderboard();
        }

    }

    private class GameAboutButtonListener implements AboutButton.AboutButtonListener {

        @Override
        public void onAbout() {
            if (GameManager.getInstance().getGameState() == GameState.OVER) {
                onGameAbout();
            } else {
                clear();
                setUpStageBase();
                setUpGameLabel();
                onGameOver();
            }
        }

    }

    private class GameShareButtonListener implements ShareButton.ShareButtonListener {

        @Override
        public void onShare() {
            GameManager.getInstance().share();
        }

    }

    private void onGamePaused() {
        GameManager.getInstance().setGameState(GameState.PAUSED);
    }

    private void onGameResumed() {
        GameManager.getInstance().setGameState(GameState.RUNNING);
    }

    private void onGameOver() {
        GameManager.getInstance().setGameState(GameState.OVER);
        GameManager.getInstance().resetDifficulty();
        totalTimePassed = 0;
        setUpMainMenu();
    }

    private void onGameAbout() {
        GameManager.getInstance().setGameState(GameState.ABOUT);
        clear();
        setUpStageBase();
        setUpGameLabel();
        setUpAboutText();
        setUpAbout();
    }

    @Override
    public void dispose() {
        super.dispose();
        AudioUtils.getInstance().disposeMusic();
    }
}