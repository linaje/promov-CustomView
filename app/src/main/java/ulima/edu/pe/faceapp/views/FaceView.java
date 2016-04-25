package ulima.edu.pe.faceapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class FaceView extends View{

    //variables que pueden ir cambiando. Son los valores de la pantalla.
    private int mAncho;
    private int mAlto;
    private int mSonrisa;

    private ScaleGestureDetector mDetector;

    //Crear clase de Java simple, heredar de View (es un CostumView) y sobreescribir los 3 primeros constructores.

    public FaceView(Context context) {
        super(context);
        mDetector = new ScaleGestureDetector(getContext(), new GestosListener());
    }

    public FaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDetector = new ScaleGestureDetector(getContext(), new GestosListener());
    }

    public FaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDetector = new ScaleGestureDetector(getContext(), new GestosListener());
    }

    //Para pintar en este componente, se usa el método onDraw()
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Pintando un círculo
        //se necesita una clase Paint para dibujar en el canvas.
        //la clase Paint especifica cómo va a hacer el pintado
        //se usa el mínimo alto o mínimo ancho para que se vea proporcionado en la pantalla, sea portrait o landscape.
        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(mAncho/4, mAlto/3, Math.min(mAlto, mAncho)/8, mPaint);
        canvas.drawCircle(mAncho*3/4, mAlto/3, Math.min(mAlto, mAncho)/8, mPaint);

        if(mSonrisa==0){
            mSonrisa = mAncho/4;
        }

        //pintando un arco
        //la clase RectF determinará las dimensiones del arco
        RectF rectF = new RectF();
        rectF.top= mAlto*2/3 - mSonrisa/2;
        rectF.left=mAncho/4;
        rectF.bottom= mAlto*2/3 + mSonrisa/2;;
        rectF.right= mAncho*3/4;

        Paint mPaintSonrisa = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintSonrisa.setColor(Color.BLACK);
        mPaintSonrisa.setStyle(Paint.Style.STROKE);
        mPaintSonrisa.setStrokeWidth(10);
        canvas.drawArc(rectF, 0, 180, false, mPaintSonrisa);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mAncho = w;
        mAlto = h;
    }

    //definir el evento, cuando interactúa con mi vista
    //esto es para colocar el evento de Pinch (aumentar/reducir zoom), esto reacciona ante cualquier gesto
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return true;
    }

    private class GestosListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            //da un factor de zoom que se está realizando
            //al hacer zoom, modificará tamaño de la sonrisa
            float scale = detector.getScaleFactor();
            mSonrisa *= scale;
            //para actualizar la pantalla
            invalidate();
            return true;
        }
    }
}
