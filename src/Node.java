import java.io.File;
import java.util.ArrayList;

public class Node {
    private Long size;
    private File folder;
    private ArrayList<Node> children;
    private int level;
    private long sizeLimit;

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public Node(File folder, long sizeLimit) {
        this(folder);
        this.sizeLimit = sizeLimit;
    }

    public Node(File folder) {
        this.folder = folder;
        children = new ArrayList<>();
    }

    public void setSizeLimit(long sizeLimit) {
        this.sizeLimit = sizeLimit;
    }

    public File getFolder() {
        return folder;
    }

    public long getSizeLimit() {
        return sizeLimit;
    }

    public void addChild(Node node) {
        node.setLevel(level + 1);
        children.add(node);
        node.setSizeLimit(sizeLimit);
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String size = SizeCalculator.getHumanReadableSize(getSize());
        builder.append(folder.getName() + " - " + size + "\n");
        for (Node child : children) {
            if (child.getSize() < sizeLimit) {
                continue;
            }
            builder.append("  ".repeat(level));
        }
        return builder.toString();
    }

}
