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
/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }
    Spatial bunny;
    protected Geometry bunnyCube;
    Boolean isRunning=true;
    int speed =3;
    Boolean isJumping = false;
    Float jumpArc = .001f;
    float bunny_starting_altitude = -3f;
    @Override
    public void simpleInitApp() {
         /** create a blue box at coordinates (1,-1,1) */
       Box box1 = new Box(1,1,1);
        Geometry bunnyCube = new Geometry("blue cube", box1);
        bunnyCube.setLocalTranslation(new Vector3f(1,-1,1));
        Material mat1 = new Material(assetManager, 
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Blue);
        bunnyCube.setMaterial(mat1);
    
        /** create a red box straight above the blue one at (1,3,1) */
        Box box2 = new Box(1,1,1);      
        Geometry red = new Geometry("Box", box2);
        red.setLocalTranslation(new Vector3f(1,3,1));
        Material mat2 = new Material(assetManager, 
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Red);
        red.setMaterial(mat2);
        
         /** Create a pivot node at (0,0,0) and attach it to the root node */
        Node pivot = new Node("pivot");
        rootNode.attachChild(pivot); // put this node in the scene

        /** Attach the two boxes to the *pivot* node. (And transitively to the root node.) */
        pivot.attachChild(bunnyCube);
        pivot.attachChild(red);
        /** Rotate the pivot node: Note that both boxes have rotated! */
        pivot.rotate(.4f,.4f,0f);
        // Create a wall with a simple texture from test_data
        
        Box box = new Box(2.5f,2.5f,1.0f);
        Spatial wall = new Geometry("Box", box );
        Material mat_brick = new Material( 
            assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_brick.setTexture("ColorMap", 
            assetManager.loadTexture("Textures/brick_texture3348.jpg"));
        wall.setMaterial(mat_brick);
        wall.setLocalTranslation(2.0f,-2.5f,0.0f);
        rootNode.attachChild(wall);
        
         // Load a model from test_data (OgreXML + material + texture)
        bunny = assetManager.loadModel("Models/bun/bun.j3o");
        bunny.scale(0.35f, 0.35f, 0.35f);
        bunny.rotate(0.0f, -3.0f, 0.0f);
        bunny.setLocalTranslation(2.0f, bunny_starting_altitude, 2.0f);
        rootNode.attachChild(bunny);
        // You must add a light to make the model visible
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        rootNode.addLight(sun);
        initKeys();
    }
/** Custom Keybinding: Map named actions to inputs. */
  private void initKeys() {
    // You can map one or several inputs to one named action
    inputManager.addMapping("Pause",  new KeyTrigger(KeyInput.KEY_P));
    inputManager.addMapping("Left",   new KeyTrigger(KeyInput.KEY_H));
    inputManager.addMapping("Right",  new KeyTrigger(KeyInput.KEY_K));
    inputManager.addMapping("Forward", new KeyTrigger(KeyInput.KEY_U));
    inputManager.addMapping("Backwards", new KeyTrigger(KeyInput.KEY_J));
    inputManager.addMapping("Rotate", new KeyTrigger(KeyInput.KEY_Y),
                                      new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
    inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
    // Add the names to the action listener.
    inputManager.addListener(actionListener, "Jump", "Pause");
    inputManager.addListener(analogListener,"Left", "Right", "Forward", "Backwards", "Rotate");

  }

  private ActionListener actionListener = new ActionListener() {
    public void onAction(String name, boolean keyPressed, float tpf) {
      if (name.equals("Pause") && !keyPressed) {
        isRunning = !isRunning;
      }
       if(isRunning && name.equals("Jump"))
        {
          triggerJump();
        }
    }
  };

  private void triggerJump()
  {
        if(!isJumping)
        {
            isJumping = true;
        }
        else isJumping = false;
        
  }
  private AnalogListener analogListener = new AnalogListener() {
  
      public void onAnalog(String name, float value, float tpf) {
      if (isRunning) {
        if (name.equals("Rotate")) {
          bunny.rotate(0, value*speed, 0);
        }
        if (name.equals("Right")) {
          Vector3f v = bunny.getLocalTranslation();
          bunny.setLocalTranslation(v.x + value*speed, v.y, v.z);
        }
        if (name.equals("Left")) {
          Vector3f v = bunny.getLocalTranslation();
          bunny.setLocalTranslation(v.x - value*speed, v.y, v.z);
        }
        if (name.equals("Forward")) {
          Vector3f v = bunny.getLocalTranslation();
          bunny.setLocalTranslation(v.x , v.y, v.z - value*speed);
        }
        if (name.equals("Backwards")) {
          Vector3f v = bunny.getLocalTranslation();
          bunny.setLocalTranslation(v.x, v.y, v.z + value*speed);
        }
      } else {
        System.out.println("Press P to unpause.");
      }
    }
  };
    @Override
    public void simpleUpdate(float tpf) {
       // bunnyCube.rotate(0, 2*tpf, 0);
        if(isJumping)
        {
            Vector3f v = bunny.getLocalTranslation();
            bunny.setLocalTranslation(v.x, v.y + jumpArc*speed*2/3, v.z);
        }
        else
        {
            Vector3f v = bunny.getLocalTranslation();
            if(v.y>bunny_starting_altitude)
            bunny.setLocalTranslation(v.x, v.y - jumpArc*speed/2, v.z);
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
