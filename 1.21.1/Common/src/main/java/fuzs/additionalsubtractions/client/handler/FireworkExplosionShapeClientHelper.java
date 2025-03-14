package fuzs.additionalsubtractions.client.handler;

import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.particle.FireworkParticles;
import net.minecraft.util.Mth;

public final class FireworkExplosionShapeClientHelper {
    static final double[][] BOLT_PARTICLE_COORDINATES = new double[][]{
            {0.0, 0.2}, {0.8, 0.0}, {-0.4, -1.0}, {0.0, -0.2}, {-0.8, 0.0}, {0.4, 1.0}, {0.0, 0.2}
    };
    static final double[][] HEART_PARTICLE_COORDINATES = new double[][]{
            {0.0, 0.5},
            {0.1, 0.5},
            {0.1, 0.7},
            {0.5, 0.7},
            {0.5, 0.5},
            {0.7, 0.5},
            {0.7, -0.1},
            {0.5, -0.1},
            {0.5, -0.3},
            {0.3, -0.3},
            {0.3, -0.5},
            {0.1, -0.5},
            {0.1, -0.7},
            {0.0, -0.7}
    };

    private FireworkExplosionShapeClientHelper() {
        // NO-OP
    }

    public static void createBoltParticleShape(FireworkParticles.Starter starter, IntList colors, IntList fadeColors, boolean trail, boolean twinkle) {
        createParticleShape(starter, 0.5, BOLT_PARTICLE_COORDINATES, colors, fadeColors, trail, twinkle, true, false);
    }

    public static void createHeartParticleShape(FireworkParticles.Starter starter, IntList colors, IntList fadeColors, boolean trail, boolean twinkle) {
        createParticleShape(starter, 0.5, HEART_PARTICLE_COORDINATES, colors, fadeColors, trail, twinkle, true, true);
    }

    /**
     * Copied from
     * {@link FireworkParticles.Starter#createParticleShape(double, double[][], IntList, IntList, boolean, boolean,
     * boolean)}.
     */
    public static void createParticleShape(FireworkParticles.Starter starter, double speed, double[][] coords, IntList colors, IntList fadeColors, boolean trail, boolean twinkle, boolean is2d, boolean mirror) {
        double d0 = coords[0][0];
        double d1 = coords[0][1];
        starter.createParticle(starter.x,
                starter.y,
                starter.z,
                d0 * speed,
                d1 * speed,
                0.0,
                colors,
                fadeColors,
                trail,
                twinkle);
        float f = starter.random.nextFloat() * (float) Math.PI;
        double d2 = is2d ? 0.034 : 0.34;

        for (int i = 0; i < 3; i++) {
            double d3 = (double) f + (double) ((float) i * (float) Math.PI) * d2;
            double d4 = d0;
            double d5 = d1;

            for (int j = 1; j < coords.length; j++) {
                double d6 = coords[j][0];
                double d7 = coords[j][1];

                for (double d8 = 0.25; d8 <= 1.0; d8 += 0.25) {
                    double d9 = Mth.lerp(d8, d4, d6) * speed;
                    double d10 = Mth.lerp(d8, d5, d7) * speed;
                    double d11 = d9 * Math.sin(d3);
                    d9 *= Math.cos(d3);

                    for (double d12 = -1.0; d12 <= (mirror ? 1.0 : 0.0); d12 += 2.0) {
                        starter.createParticle(starter.x,
                                starter.y,
                                starter.z,
                                d9 * d12,
                                d10,
                                d11 * d12,
                                colors,
                                fadeColors,
                                trail,
                                twinkle);
                    }
                }

                d4 = d6;
                d5 = d7;
            }
        }
    }
}
