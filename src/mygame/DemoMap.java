/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
/**
 *
 * @author Kelly
 */
public class DemoMap {
    private final SimpleApplication app;
    private Material redMat;
    private Material blueMat;
    private Material greenMat;
    private Material yellowMat;
    private Material whiteMat;
    private Material grayMat;
    private Material brickMat;
    private Node mapNode;
    public DemoMap(SimpleApplication app)
    {
        this.app=app;
        this.mapNode = new Node();
        initMaterials();
        mapNode.attachChild(buildCage());
    }
    public Node getNode()
    {
        return mapNode;
    }
    private AssetManager getAssetManager()
    {
        return app.getAssetManager();
    }
    private void initMaterials()
    {
      redMat = new Material(getAssetManager(), 
                "Common/MatDefs/Misc/Unshaded.j3md");
      redMat.setColor("Color", ColorRGBA.Red);
      blueMat = new Material(getAssetManager(), 
                "Common/MatDefs/Misc/Unshaded.j3md");
      blueMat.setColor("Color", ColorRGBA.Blue);
      greenMat = new Material(getAssetManager(), 
                "Common/MatDefs/Misc/Unshaded.j3md");
      greenMat.setColor("Color", ColorRGBA.Green);
      yellowMat = new Material(getAssetManager(), 
                "Common/MatDefs/Misc/Unshaded.j3md");
      yellowMat.setColor("Color", ColorRGBA.Yellow);
      whiteMat = new Material(getAssetManager(), 
                "Common/MatDefs/Misc/Unshaded.j3md");
      whiteMat.setColor("Color", ColorRGBA.White);
      grayMat = new Material(getAssetManager(), 
                "Common/MatDefs/Misc/Unshaded.j3md");
      grayMat.setColor("Color", ColorRGBA.Gray);
      brickMat = new Material(getAssetManager(), 
                "Common/MatDefs/Misc/Unshaded.j3md");
      brickMat.setTexture("ColorMap", 
            getAssetManager().loadTexture("Textures/brick_texture3348.jpg"));

    }
    
    private Node buildCage()
    {
        float cageX = 6.0f;
        float cageY = 3.0f;
        float cageZ = 8.0f;
        float cageThickness =0.5f;
        
        Box cageWallSmall = new Box(cageX,cageY,cageThickness);
        Box cageWallLarge = new Box(cageThickness,cageY,cageZ);
        Box cageWallRoof= new Box(cageX,cageThickness,cageZ);
       
        Spatial smallCageWall1 = new Geometry("Cage Wall (Small)", cageWallSmall);
        Spatial smallCageWall2 = new Geometry("Cage Wall  (Small)", cageWallSmall);
        Spatial largeCageWall1 = new Geometry("Cage Wall (Large)", cageWallLarge);
        Spatial largeCageWall2 = new Geometry("Cage Wall (Large)", cageWallLarge);
        Spatial roofCageWall = new Geometry("Cage Wall (Roof)", cageWallRoof);
        Spatial floorCageWall = new Geometry("Cage Wall (Floor)", cageWallRoof);
       
        smallCageWall1.setMaterial(redMat);
        smallCageWall2.setMaterial(yellowMat);
        largeCageWall1.setMaterial(greenMat);
        largeCageWall2.setMaterial(blueMat);
        roofCageWall.setMaterial(whiteMat);
        floorCageWall.setMaterial(brickMat);
        
        smallCageWall1.setLocalTranslation(new Vector3f(0,0,cageZ));
        smallCageWall2.setLocalTranslation(new Vector3f(0,0,-cageZ));
        largeCageWall1.setLocalTranslation(new Vector3f(cageX,0, 0));
        largeCageWall2.setLocalTranslation(new Vector3f(-cageX,0,0));
        roofCageWall.setLocalTranslation(new Vector3f(0,cageY+cageThickness,0));
        floorCageWall.setLocalTranslation(new Vector3f(0,-cageY-cageThickness,0));
        
        Node cage = new Node();
        cage.attachChild(smallCageWall1);
        cage.attachChild(smallCageWall2);
        cage.attachChild(largeCageWall1);
        cage.attachChild(largeCageWall2);
        cage.attachChild(roofCageWall);
        cage.attachChild(floorCageWall);
        return cage;
    }
}
