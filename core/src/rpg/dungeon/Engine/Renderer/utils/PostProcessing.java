package rpg.dungeon.Engine.Renderer.utils;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.crashinvaders.vfx.VfxManager;
import com.crashinvaders.vfx.effects.BloomEffect;
import com.crashinvaders.vfx.effects.CrtEffect;
import com.crashinvaders.vfx.effects.FxaaEffect;
import com.crashinvaders.vfx.effects.RadialDistortionEffect;
import com.crashinvaders.vfx.effects.VignettingEffect;
import rpg.dungeon.Engine.utils.Timer;

/**
 * Created by philipp on 24.01.15.
 */
public class PostProcessing {
    public VfxManager vfxManager;
    public CrtEffect crt;
    public RadialDistortionEffect curvature;
    public BloomEffect bloom;
    public VignettingEffect vignette;
    public FxaaEffect fxaa;
    public Timer timer = new Timer();
    public Color bg = new Color(0,0,0,1);

    public PostProcessing(Color Backgound){
        bg = Backgound;
    }

    public void begin() {
        Gdx.gl.glClearColor(bg.r,bg.g,bg.b,bg.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            timer.update(Gdx.graphics.getDeltaTime());
            vfxManager.cleanUpBuffers(bg);
            vfxManager.beginInputCapture();
        }
    }

    public void end() {
        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            vfxManager.endInputCapture();
            vfxManager.applyEffects();
            vfxManager.renderToScreen();
        }
    }

    public void resize(float UnitsPerPixel) {
        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            if(vfxManager == null) {
                vfxManager = new VfxManager(Pixmap.Format.RGBA8888);

                fxaa = new FxaaEffect();
                bloom = new BloomEffect();
                bloom.setBlurAmount(0.5f);
                bloom.setBaseIntensity(1f);
                bloom.setBaseSaturation(0.85f);
                bloom.setBloomIntensity(2f);
                bloom.setBloomSaturation(0.85f);
                vignette = new VignettingEffect(false);
                // gdx-vfx 0.5.4 CrtEffect calls setUniformf("u_resolution") in rebind(), but that
                // uniform is absent from the VIEWPORT-mode shader. libGDX 1.14 throws on missing
                // uniforms by default, so pedantic mode is disabled just for this constructor call.
                ShaderProgram.pedantic = false;
                crt = new CrtEffect(CrtEffect.LineStyle.HORIZONTAL_SMOOTH, 0.9f, 0.9f);
                ShaderProgram.pedantic = true;
                curvature = new RadialDistortionEffect();
                curvature.setDistortion(0.15f);

                vfxManager.addEffect(bloom);
                vfxManager.addEffect(vignette);
                vfxManager.addEffect(crt);
                vfxManager.addEffect(curvature);
                vfxManager.addEffect(fxaa);
            }

            bloom.setBlurPasses(Math.round(1f / UnitsPerPixel * 4));
            vfxManager.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    public void dispose() {
        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            vfxManager.dispose();
            bloom.dispose();
            vignette.dispose();
            crt.dispose();
            curvature.dispose();
            fxaa.dispose();
        }
    }

    public void resume() {
        if(Gdx.app.getType() == Application.ApplicationType.Desktop) {
            vfxManager.rebind();
        }
    }
}
