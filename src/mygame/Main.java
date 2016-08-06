package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.Node;
import com.jme3.light.DirectionalLight;
import com.jme3.scene.Spatial;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.font.BitmapText;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }
    Player player;
    Boolean isRunning=true;
    float camAbove =2.5f;
    float camBehind =3.5f;
    float camCenter = 2.0f;
    @Override
    public void simpleInitApp() {
        initCrossHairs();

        // You must add a light to make the model visible
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        rootNode.addLight(sun);
        initKeys();
        initPlayer();
        initMap();
    }
    private void initPlayer()
    {
        Spatial bunny = assetManager.loadModel("Models/bun/bun.j3o");
        bunny.scale(0.35f, 0.35f, 0.35f);
        bunny.rotate(0.0f, -3.0f, 0.0f);
        //rootNode.attachChild(bunny);
        player = new Player(bunny);
        rootNode.attachChild(player.getNode());
    }
    private void initMap()
    {
        DemoMap map = new DemoMap(this);
        rootNode.attachChild(map.getNode());
    }
     /** A centred plus sign to help the player aim. */
  protected void initCrossHairs() {
    setDisplayStatView(false);
    guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
    BitmapText ch = new BitmapText(guiFont, false);
    ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
    ch.setText("+"); // crosshairs
    ch.setLocalTranslation( // center
      settings.getWidth() / 2 - ch.getLineWidth()/2, settings.getHeight() / 2 + ch.getLineHeight()/2, 0);
    guiNode.attachChild(ch);
  }
/** Custom Keybinding: Map named actions to inputs. */
  private void initKeys() {
       inputManager.deleteMapping( SimpleApplication.INPUT_MAPPING_MEMORY );
    // You can map one or several inputs to one named action
    inputManager.addMapping("Pause",  new KeyTrigger(KeyInput.KEY_P));
    inputManager.addMapping("Left",   new KeyTrigger(KeyInput.KEY_A));
    inputManager.addMapping("Right",  new KeyTrigger(KeyInput.KEY_D));
    inputManager.addMapping("Forward", new KeyTrigger(KeyInput.KEY_W));
    inputManager.addMapping("Backwards", new KeyTrigger(KeyInput.KEY_S));
    inputManager.addMapping("Rotate", new KeyTrigger(KeyInput.KEY_Y),
                                      new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
    inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
    // Add the names to the action listener.
    inputManager.addListener(actionListener, "Pause");
    inputManager.addListener(analogListener,"Left", "Right", "Forward", "Backwards", "Rotate",  "Jump");
      
  }

  private ActionListener actionListener = new ActionListener() {
    public void onAction(String name, boolean keyPressed, float tpf) {
      if (name.equals("Pause") && !keyPressed) {
        isRunning = !isRunning;
      }
    }
  };

  private AnalogListener analogListener = new AnalogListener() {
      public void onAnalog(String name, float value, float tpf) {
      if (isRunning) {
        if (name.equals("Right")) {
           player.rotateRight(value, tpf);
        }
        if (name.equals("Left")) {
          player.rotateLeft(value, tpf);
        }
        if (name.equals("Forward")) {
          player.moveForward(value, tpf);
        }
        if (name.equals("Backwards")) {
          player.moveBackwards(value, tpf);
        }
        if (name.equals("Backwards")) {
          player.jump(value, tpf);
        }
      } else {
        System.out.println("Press P to unpause.");
      }
    }
  };
    @Override
    public void simpleUpdate(float tpf) {
        player.update();
        Vector3f v = player.getLocation();
        cam.setLocation(new Vector3f(v.x + camCenter, camAbove, v.z+camBehind));
        //TODO cam rotation
    }

    @Override
    public void simpleRender(RenderManager rm) {
        player.render();
    }
}
