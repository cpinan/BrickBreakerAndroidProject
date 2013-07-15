package com.carlospinan.brickbreakerandroid.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.carlospinan.brickbreakerandroid.MainActivity;
import com.carlospinan.brickbreakerandroid.configs.GameConfig;
import com.carlospinan.brickbreakerandroid.engine.SurfaceClass;
import com.carlospinan.brickbreakerandroid.game.sprites.Ball;
import com.carlospinan.brickbreakerandroid.game.sprites.Block;
import com.carlospinan.brickbreakerandroid.game.sprites.Paddle;
import com.carlospinan.brickbreakerandroid.interfaces.IBaseGame;
import com.carlospinan.brickbreakerandroid.utils.SoundManager;
import com.carlospinan.brickbreakerandroid.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 14/07/13.
 */
public class BrickBreakerGame implements IBaseGame {

    private int TOTAL_BLOCKS = 6;

    // Bitmaps
    private Bitmap bmpPlayer;
    private Bitmap bmpBall;
    private Bitmap bmpBlock;
    private SurfaceClass reference;

    // Sprites
    private Paddle paddle;
    private Ball ball;

    // Lista de bloques
    private List<Block> blocks;

    // Puntaje
    private int score;
    private Paint p_score;

    public BrickBreakerGame(SurfaceClass reference, Context context) {
        this.reference = reference;
        _load(context);
    }

    private void _load(Context context) {
        // Datos para el score
        score = 0;
        p_score = new Paint();
        p_score.setTextSize(30.0f);
        p_score.setARGB(255, 255, 0, 0);
        p_score.setTextAlign(Paint.Align.CENTER);
        Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);
        p_score.setTypeface(tf);

        // Cargamos los sonidos
        SoundManager.preloadSFX(context, Assets.SFX_HIT);
        SoundManager.preloadSFX(context, Assets.SFX_COIN);

        // Bitmap data
        bmpPlayer = Utils.load(context, "paddle.png");
        bmpBall = Utils.load(context, "ball.png");
        bmpBlock = Utils.load(context, "block.png");
        // Sprites players
        paddle = new Paddle(bmpPlayer);
        ball = new Ball(bmpBall);

        _crearBloques();

        // Colocamos las posiciones iniciales
        paddle.setPosition(GameConfig.SCREEN_WIDTH * 0.5f, GameConfig.SCREEN_HEIGHT - paddle.getHeight() * 0.5f);
        ball.reset();

    }

    private void _crearBloques() {
        // Lista de bloques
        float block_x = bmpBlock.getWidth() * 1.3f;
        float block_y = bmpBlock.getHeight() * 1.2f;
        blocks = new ArrayList<Block>(TOTAL_BLOCKS);
        for (int i = 1; i <= TOTAL_BLOCKS; i++) {
            Block b = new Block(bmpBlock);
            b.setPosition(block_x, block_y);
            blocks.add(b);
            block_x += bmpBlock.getWidth() * 1.1f;
            if (i % 6 == 0) {
                block_y += bmpBlock.getHeight() * 1.2f;
                block_x = bmpBlock.getWidth() * 1.3f;
            }
        }
    }

    @Override
    public void update(float dt) {
        _input();

        ball.update(dt);

        _colisiones();
        _verificar_juego();
    }

    private void _input() {
        paddle.move(reference.getAccelerometer());
    }

    private void _colisiones() {
        // Colision entre la paleta y la pelota
        if (paddle.getBounds().intersect(ball.getBounds())) {
            MainActivity.vibrate(100);
            SoundManager.playSFX(Assets.SFX_HIT);
            ball.move(paddle);
            ball.setPosition(ball.getX(), paddle.getY() - paddle.getHeight());
        }

        // Colision entre la pelota y alguno de los bloques
        List<Block> destroy_blocks = new ArrayList<Block>();
        for (Block block : blocks) {
            if (block.getState() != Block.STATE_DESTROYED && ball.getBounds().intersect(block.getBounds())) {
                score++;
                SoundManager.playSFX(Assets.SFX_COIN);
                ball.invertSpeed();
                block.destroy();
                block.setVisible(false);
                destroy_blocks.add(block);
            }
        }

        // Elminación de los bloques destruidos del array principal
        for (Block block : destroy_blocks) {
            blocks.remove(block);
        }
    }

    private void _verificar_juego() {
        if (blocks.isEmpty()) {
            if (TOTAL_BLOCKS < 36)
                TOTAL_BLOCKS += 6;
            _crearBloques();
            ball.reset();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        paddle.draw(canvas);
        ball.draw(canvas);
        for (Block block : blocks) {
            block.draw(canvas);
        }

        canvas.drawText("Puntaje: " + score, GameConfig.SCREEN_WIDTH * 0.5f, 300, p_score);

    }

    public void onPause() {
        SoundManager.pause();
    }

    public void onDestroy() {
        // Release bmpPlayer
        if (bmpPlayer != null)
            bmpPlayer.recycle();
        bmpPlayer = null;

        // Release bmpBall
        if (bmpBall != null)
            bmpBall.recycle();
        bmpBall = null;

        // Release bmpBall
        if (bmpBlock != null)
            bmpBlock.recycle();
        bmpBlock = null;

        // Release bloques
        blocks.clear();

        SoundManager.releaseSounds();

    }
}
