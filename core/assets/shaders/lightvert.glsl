attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;
uniform mat4 u_projTrans;
uniform float strength[MaxLights];
uniform float intensity[MaxLights];
uniform vec2 Lights[MaxLights];
uniform vec4 u_color;
varying vec4 v_color;
varying vec2 v_texCoords;

void main()
{
    float value = 0.0f;
    for(int i=0;i<int(MaxLights);i++)
    {
      value += max((1.0f-(distance(a_position.xy,Lights[i])/strength[i]))*intensity[i],0.0f);
    }
    vec3 bright = u_color.xyz * value;
    v_color = a_color * vec4(bright,1.0f);
   v_color.a = v_color.a * (255.0f/254.0f);
   v_texCoords = a_texCoord0;
   gl_Position =  u_projTrans * a_position;
}