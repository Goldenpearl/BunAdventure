/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.scene.Node;
import com.jme3.math.Quaternion;
/**
 *
 * @author Kelly
 */
public class Player{
    
    private static final float BUNNY_STARTING_ALTITUDE=-2.0f;
    private static final Vector3f STARTING_LOCATION = new Vector3f(0.0f, BUNNY_STARTING_ALTITUDE, 0.0f);
    private static final float BUNNY_RUN_SPEED = 10.0f;
    private static final float BUNNY_FALL_SPEED = 0.20f;
    private static final float BUNNY_JUMP_SPEED = 0.20f;
    private static final float BUNNY_ROTATE_SPEED = 3.0f;
    private Boolean isJumping;
    private Spatial playerAvatar;
    private Node playerNode;
    public Player(Spatial playerAvatar)
    { 
        this.playerAvatar = playerAvatar;
        isJumping=false;
        playerNode = new Node();
        playerNode.attachChild(this.playerAvatar);
        playerNode.setLocalTranslation(STARTING_LOCATION);
    }
    public Spatial getAvatar()
    {
        return playerAvatar;
    }
    
    public Node getNode()
    {
        return playerNode;
    }
    public void render()
    {
        
    }
    
    public void setLocation(Vector3f v)
    {
        playerNode.setLocalTranslation(v);
    }
    
    public Vector3f getLocation()
    {
        return playerNode.getLocalTranslation();
    }
    
    public Quaternion getRotation()
    {
        return playerNode.getLocalRotation();
    }
    public void moveForward(float keyPressed, float tpf)
    {
         Vector3f forward = playerNode.getLocalRotation().getRotationColumn(2);
         playerNode.move(forward.mult(-1*keyPressed*BUNNY_RUN_SPEED)); //later, mult by tpf, or timeperframe
    }
    
    public void moveBackwards(float keyPressed, float tpf)
    {
         Vector3f forward = playerNode.getLocalRotation().getRotationColumn(2);
         playerNode.move(forward.mult(keyPressed*BUNNY_RUN_SPEED)); //later, mult by tpf, or timeperframe
    }
    
    public void rotateLeft(float keyPressed, float tpf)
    {
         playerNode.rotate(0, keyPressed*BUNNY_ROTATE_SPEED, 0);
    }
    
    public void rotateRight(float keyPressed, float tpf)
    {
         playerNode.rotate(0, -1.0f*keyPressed *BUNNY_ROTATE_SPEED, 0);
    }
    
    public void jump(float keyPressed, float tpf)
    {
         //Vector3f up = playerNode.getLocalRotation().getRotationColumn(1);
         //playerNode.move(up.mult(keyPressed*BUNNY_RUN_SPEED)); //later, mult by tpf, or timeperframe
    }

    
    public void update()
    {
       
    }
    
    
}
