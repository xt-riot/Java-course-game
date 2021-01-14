import javax.swing.*;

public class EndScreen extends Panel {
    // dilose edw tin metavliti sou

    EndScreen(JFrame id) {
        super(id, true);
        // arxikopoihsetin edw
        // prosthese tin sto frame
    }

    @Override
    public void startRendering() {
        this.imgCurrentPosition = 150;
        super.startRenderingImage(150, false);
        // tha sou emfanistei miafora, ta animations tha ta allaksw meta
        // pekse mpala mono me to pws fainetai h klasi sou
    }

    @Override
    public void unRender(int delay) {

    }
}
