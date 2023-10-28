import java.awt.BasicStroke;
import java.awt.Color;
            import java.awt.Dimension;
            import java.awt.Graphics;
            import java.awt.Graphics2D;
            import java.awt.Point;
            import java.awt.RenderingHints;
            import java.awt.event.MouseAdapter;
            import java.awt.event.MouseEvent;
            import java.awt.event.MouseMotionAdapter;
            import java.util.ArrayList;
            import javax.swing.JPanel;

            public class CustomGraphicsPanel extends JPanel {
                private ArrayList<Point> points;
                private ArrayList<String> texts;
                private Color lineColor;
                private int lineThickness;
                private boolean isDrawing;
                private Point startPoint;
                private Point endPoint;
                private String currentText;

                public CustomGraphicsPanel() {
                    points = new ArrayList<Point>();
                    texts = new ArrayList<String>();
                    lineColor = Color.BLACK;
                    lineThickness = 1;
                    isDrawing = false;
                    currentText = "";
                    setPreferredSize(new Dimension(400, 400));
                    setBackground(Color.WHITE);
                    addMouseListener(new MouseAdapter() {
                        public void mousePressed(MouseEvent e) {
                            startPoint = e.getPoint();
                            isDrawing = true;
                        }

                        public void mouseReleased(MouseEvent e) {
                            endPoint = e.getPoint();
                            isDrawing = false;
                            points.add(startPoint);
                            points.add(endPoint);
                            texts.add(currentText);
                            currentText = "";
                            repaint();
                        }
                    });
                    addMouseMotionListener(new MouseMotionAdapter() {
                        public void mouseDragged(MouseEvent e) {
                            endPoint = e.getPoint();
                            repaint();
                        }
                    });
                }

                public void setLineColor(Color color) {
                    lineColor = color;
                }

                public void setLineThickness(int thickness) {
                    lineThickness = thickness;
                }

                public void setCurrentText(String text) {
                    currentText = text;
                }

                public void clear() {
                    points.clear();
                    texts.clear();
                    repaint();
                }

                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(lineColor);
                    g2d.setStroke(new BasicStroke(lineThickness));
                    for (int i = 0; i < points.size() - 1; i += 2) {
                        Point p1 = points.get(i);
                        Point p2 = points.get(i + 1);
                        g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
                    }
                    g2d.setColor(Color.BLACK);
                    for (int i = 0; i < texts.size(); i++) {
                        String text = texts.get(i);
                        Point p = points.get(i * 2);
                        g2d.drawString(text, p.x, p.y);
                    }
                    if (isDrawing) {
                        g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
                        g2d.drawString(currentText, endPoint.x, endPoint.y);
                    }
                }
            }