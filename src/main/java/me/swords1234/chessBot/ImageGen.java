package me.swords1234.chessBot;

import me.swords1234.chessBot.pieces.*;
import me.swords1234.chessBot.utils.enums.Type;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageGen {

    BufferedImage bishop;
    BufferedImage rook;
    BufferedImage pawn;
    BufferedImage knight;
    BufferedImage king;
    BufferedImage queen;
    BufferedImage board;

    BufferedImage bishopW;
    BufferedImage rookW;
    BufferedImage pawnW;
    BufferedImage knightW;
    BufferedImage kingW;
    BufferedImage queenW;

    int width = 170;
    int height = 170;

    public ImageGen() {
        try {
            File file = new File(ImageGen.class.getClassLoader().getResource("./Chess.png").getFile());
            board = ImageIO.read(file);
            file = new File(ImageGen.class.getClassLoader().getResource("./Bishop.png").getFile());
            bishop = ImageIO.read(file);
            file = new File(ImageGen.class.getClassLoader().getResource("./Castle.png").getFile());
            rook = ImageIO.read(file);
            file = new File(ImageGen.class.getClassLoader().getResource("./Horse.png").getFile());
            knight = ImageIO.read(file);
            file = new File(ImageGen.class.getClassLoader().getResource("./King.png").getFile());
            king = ImageIO.read(file);
            file = new File(ImageGen.class.getClassLoader().getResource("./Pawn.png").getFile());
            pawn = ImageIO.read(file);
            file = new File(ImageGen.class.getClassLoader().getResource("./Queen.png").getFile());
            queen = ImageIO.read(file);



            file = new File(ImageGen.class.getClassLoader().getResource("./Bishop_WHITE.png").getFile());
            bishopW = ImageIO.read(file);
            file = new File(ImageGen.class.getClassLoader().getResource("./Castle_WHITE.png").getFile());
            rookW = ImageIO.read(file);
            file = new File(ImageGen.class.getClassLoader().getResource("./Horse_WHITE.png").getFile());
            knightW = ImageIO.read(file);
            file = new File(ImageGen.class.getClassLoader().getResource("./King_WHITE.png").getFile());
            kingW = ImageIO.read(file);
            file = new File(ImageGen.class.getClassLoader().getResource("./Pawn_WHITE.png").getFile());
            pawnW = ImageIO.read(file);
            file = new File(ImageGen.class.getClassLoader().getResource("./Queen_WHITE.png").getFile());
            queenW = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public BufferedImage genImage(Peice[] peices) {
        BufferedImage boards = board;
        for (int i = 0; i < peices.length; i++) {
            Peice peice = peices[i];
            int x = i%8;
            int y = i/8;
            int drawX = 17 + x * 17;
            int drawY = 170 - (y * 17) - 34;
            Image peiceImg;

            if (peice instanceof Pawn) {
                peiceImg = peice.getType() == Type.WHITE ? pawnW : pawn;
            } else if (peice instanceof Bishop) {
                peiceImg = peice.getType() == Type.WHITE ? bishopW : bishop;
            } else if (peice instanceof King) {
                peiceImg = peice.getType() == Type.WHITE ? kingW : king;
            } else if (peice instanceof Queen) {
                peiceImg = peice.getType() == Type.WHITE ? queenW : queen;
            } else if (peice instanceof Knight) {
                peiceImg = peice.getType() == Type.WHITE ? knightW : knight;
            } else if (peice instanceof Rook) {
                peiceImg = peice.getType() == Type.WHITE ? rookW : rook;
            } else {
                continue;
            }

            boards.getGraphics().drawImage(peiceImg, drawX, drawY, null);
        }
        File f = new File(ImageGen.class.getClassLoader().getResource("./Chess.png").getFile());
        try {
            board = ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return boards;
    }

    public BufferedImage genImageUpsideDown(Peice[] peices) {
        BufferedImage boards = board;
        boards = rotateImageByDegrees(boards, 180);
        for (int i = 0; i < peices.length; i++) {
            Peice peice = peices[i];
            int x = i%8;
            int y = i/8;
            int drawX = 170 - (x * 17) - 34;
            int drawY = (17 + y * 17);
            Image peiceImg = image(peice);

            boards.getGraphics().drawImage(peiceImg, drawX, drawY, null);
        }
        File f = new File(ImageGen.class.getClassLoader().getResource("./Chess.png").getFile());
        try {
            board = ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return boards;
    }

    public BufferedImage rotateImageByDegrees(BufferedImage img, double angle) {

        double rads = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);

        int x = w / 2;
        int y = h / 2;

        at.rotate(rads, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0, null);
        g2d.setColor(Color.RED);
        g2d.drawRect(0, 0, newWidth - 1, newHeight - 1);
        g2d.dispose();

        return rotated;
    }

    public BufferedImage genBoth(Peice[] peices, List<Peice> removed) {
        BufferedImage image = new BufferedImage(width*2 + 8 + (16*4), height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = image.createGraphics();
        int white = 0;
        int black = 0;
        int whiteOff = 0;
        int blackOff = 0;
        for (Peice peice : removed) {
            if (peice.type == Type.WHITE) {
                BufferedImage images = image(peice);
                if (white >= 10) {
                    whiteOff = 16;
                    white = 0;
                }
                graphics2D.drawImage(images, width*2 + 8 + whiteOff, white*16, null);
                white++;
            } else if (peice.type == Type.BLACK){
                BufferedImage images = image(peice);
                if (black >= 10) {
                    blackOff = 16;
                    black = 0;
                }
                graphics2D.drawImage(images, width*2+8+2*16+blackOff, black*16, null);
                black++;
            }
        }
        graphics2D.drawImage(genImage(peices), 0, 0, null);
        graphics2D.drawImage(genImageUpsideDown(peices), width + 8, 0, null);
        return image;
    }

    public BufferedImage image(Peice peice) {
        BufferedImage peiceImg;
        if (peice instanceof Pawn) {
            peiceImg = peice.getType() == Type.WHITE ? pawnW : pawn;
        } else if (peice instanceof Bishop) {
            peiceImg = peice.getType() == Type.WHITE ? bishopW : bishop;
        } else if (peice instanceof King) {
            peiceImg = peice.getType() == Type.WHITE ? kingW : king;
        } else if (peice instanceof Queen) {
            peiceImg = peice.getType() == Type.WHITE ? queenW : queen;
        } else if (peice instanceof Knight) {
            peiceImg = peice.getType() == Type.WHITE ? knightW : knight;
        } else if (peice instanceof Rook) {
            peiceImg = peice.getType() == Type.WHITE ? rookW : rook;
        } else {
            peiceImg = null;
        }
        return peiceImg;
    }
    
}
