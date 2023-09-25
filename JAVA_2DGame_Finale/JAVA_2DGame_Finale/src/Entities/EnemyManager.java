package Entities;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import GameStates.Playing;
import Levels.Level;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {

	private Playing playing;
	private BufferedImage[][] soldierArr, pinkstarArr, sharkArr;
	private Level currentLevel;

	public EnemyManager(Playing playing) {
		this.playing = playing;
		loadEnemyImgs();
	}

	public void loadEnemies(Level level) {
		this.currentLevel = level;
	}

	public void update(int[][] lvlData) {
		boolean isAnyActive = false;
		for (Soldier c : currentLevel.getSoldiers())
			if (c.isActive()) {
				c.update(lvlData, playing);
				isAnyActive = true;
			}

		for (Pinkstar p : currentLevel.getPinkstars())
			if (p.isActive()) {
				p.update(lvlData, playing);
				isAnyActive = true;
			}

		for (Shark s : currentLevel.getSharks())
			if (s.isActive()) {
				s.update(lvlData, playing);
				isAnyActive = true;
			}

		if (!isAnyActive)
			playing.setLevelCompleted(true);
	}

	public void draw(Graphics g, int xLvlOffset) {
		drawSoldiers(g, xLvlOffset);
		drawPinkstars(g, xLvlOffset);
		drawSharks(g, xLvlOffset);
	}

	private void drawSharks(Graphics g, int xLvlOffset) {
		for (Shark s : currentLevel.getSharks())
			if (s.isActive()) {
				g.drawImage(sharkArr[s.getState()][s.getAniIndex()], (int) s.getHitbox().x - xLvlOffset - SHARK_DRAWOFFSET_X + s.flipX(),
						(int) s.getHitbox().y - SHARK_DRAWOFFSET_Y + (int) s.getPushDrawOffset(), SHARK_WIDTH * s.flipW(), SHARK_HEIGHT, null);
			}
	}

	private void drawPinkstars(Graphics g, int xLvlOffset) {
		for (Pinkstar p : currentLevel.getPinkstars())
			if (p.isActive()) {
				g.drawImage(pinkstarArr[p.getState()][p.getAniIndex()], (int) p.getHitbox().x - xLvlOffset - PINKSTAR_DRAWOFFSET_X + p.flipX(),
						(int) p.getHitbox().y - PINKSTAR_DRAWOFFSET_Y + (int) p.getPushDrawOffset(), PINKSTAR_WIDTH * p.flipW(), PINKSTAR_HEIGHT, null);
//				p.drawHitbox(g, xLvlOffset);
			}
	}

	private void drawSoldiers(Graphics g, int xLvlOffset) {
		for (Soldier c : currentLevel.getSoldiers())
			if (c.isActive()) {

				g.drawImage(soldierArr[c.getState()][c.getAniIndex()], (int) c.getHitbox().x - xLvlOffset - SOLDIER_DRAWOFFSET_X + c.flipX(),
						(int) c.getHitbox().y - SOLDIER_DRAWOFFSET_Y + (int) c.getPushDrawOffset(), SOLDIER_WIDTH * c.flipW(), SOLDIER_HEIGHT, null);

			}

	}

	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		for (Soldier c : currentLevel.getSoldiers())
			if (c.isActive())
				if (c.getState() != DEAD && c.getState() != HIT)
					if (attackBox.intersects(c.getHitbox())) {
						c.hurt(20);
						return;
					}

		for (Pinkstar p : currentLevel.getPinkstars())
			if (p.isActive()) {
				if (p.getState() == ATTACK && p.getAniIndex() >= 3)
					return;
				else {
					if (p.getState() != DEAD && p.getState() != HIT)
						if (attackBox.intersects(p.getHitbox())) {
							p.hurt(20);
							return;
						}
				}
			}

		for (Shark s : currentLevel.getSharks())
			if (s.isActive()) {
				if (s.getState() != DEAD && s.getState() != HIT)
					if (attackBox.intersects(s.getHitbox())) {
						s.hurt(20);
						return;
					}
			}
	}

	private void loadEnemyImgs() {
		soldierArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.SOLDIER_SPRITE), 9, 5, SOLDIER_WIDTH_DEFAULT, SOLDIER_HEIGHT_DEFAULT);
		pinkstarArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.PINKSTAR_ATLAS), 8, 5, PINKSTAR_WIDTH_DEFAULT, PINKSTAR_HEIGHT_DEFAULT);
		sharkArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.SHARK_ATLAS), 8, 5, SHARK_WIDTH_DEFAULT, SHARK_HEIGHT_DEFAULT);
	}

	private BufferedImage[][] getImgArr(BufferedImage atlas, int xSize, int ySize, int spriteW, int spriteH) {
		BufferedImage[][] tempArr = new BufferedImage[ySize][xSize];
		for (int j = 0; j < tempArr.length; j++)
			for (int i = 0; i < tempArr[j].length; i++)
				tempArr[j][i] = atlas.getSubimage(i * spriteW, j * spriteH, spriteW, spriteH);
		return tempArr;
	}

	public void resetAllEnemies() {
		for (Soldier c : currentLevel.getSoldiers())
			c.resetEnemy();
		for (Pinkstar p : currentLevel.getPinkstars())
			p.resetEnemy();
		for (Shark s : currentLevel.getSharks())
			s.resetEnemy();
	}

}
