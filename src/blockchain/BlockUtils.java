package blockchain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BlockUtils extends Block {

    List<Block> blockchain = new ArrayList<>();

    public BlockUtils() {
    }

    //Constructor
    public BlockUtils(int index, String data, String previousHash) throws IOException {
        super(index, data, previousHash);
    }

    // to get List of Blocks
    public final List<Block> getBlockChain() {
        return blockchain;
    }

    // get the last Block in BlockChain from file
    public Block getLatestBlock() throws IOException {
        String last = null, line;
        Block b;
        try (BufferedReader in = new BufferedReader(new FileReader("file path.txt"))) {
            b = null;
            while ((line = in.readLine()) != null) {
                if (line != null) {
                    last = line;
                }
            }
        }
        String[] attributes = last.split(",");
        b = new Block(Integer.parseInt(attributes[0]), attributes[3], attributes[2]);
        return b;
    }

    // get the last Block Hash in BlockChain from file
    public String getLatestHash() throws FileNotFoundException, IOException {
        String last = null, line;
        try (BufferedReader in = new BufferedReader(new FileReader("file path.txt"))) {
            while ((line = in.readLine()) != null) {
                if (line != null) {
                    last = line;
                }
            }
        }
        String[] attributes = last.split(",");
        return attributes[1];
    }

    // to create first Block in Chain (Genesis Block)
    public void createGenesisBlock() throws FileNotFoundException, IOException {
        File file = new File("file path.txt");
        InputStreamReader streamReader = new InputStreamReader(new FileInputStream(file));
        BufferedReader br = new BufferedReader(streamReader);
        if (br.readLine() == null) {
            getBlockChain().add(new Block(0, "This is a Genesis Block !!", "0"));
            saveBlockChain();
        }
    }

    // to add Blocks in BlockChain
    public void addBlock(String data) throws IOException {
        Block previousBlock = getLatestBlock();
        Block newBlock = new Block(previousBlock.getIndex() + 1, data, getLatestHash());
        blockchain.add(newBlock);
        saveBlockChain();
    }

    // show all blocks from file
    public void blocksExplorer() throws IOException {
        FileReader fr;
        String result = "";
        try {
            fr = new FileReader(new File("file path.txt"));
            try (BufferedReader br = new BufferedReader(fr)) {
                String line = br.readLine();
                while (line != null) {
                    String[] attributes = line.split(",");
                    System.out.println("\n+------------------------------------------------------------------------------------+\n"
                            + "|  Block name: " + attributes[0] + "\n"
                            + "|  Block Hash: " + attributes[1] + "\n"
                            + "|  Block previousHash: " + attributes[2] + "\n"
                            + "|  Block Data: { " + attributes[3] + " }\n"
                            + "|  Block nonce: " + attributes[4] + "\n"
                            + "|  Block TimeStamp: " + attributes[5] + "\n"
                            + "+-------------------------------------------------------------------------------------+\n");
                    line = br.readLine();
                }
            }
        } catch (IOException e) {
        }
    }

    // to know the chain is valid or not by (hash and previous has) to every block
    private boolean isChainValid() throws IOException {
        List<String> currentBlockHash = new ArrayList<>();
        List<String> previousBlockHash = new ArrayList<>();
        try (BufferedReader bufReader = new BufferedReader(new FileReader("file path.txt"))) {
            String line = bufReader.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                currentBlockHash.add(attributes[1]);
                previousBlockHash.add(attributes[2]);
                line = bufReader.readLine();
            }
        }
        for (int i = previousBlockHash.size() - 1; i > 0; i--) {
            if (!previousBlockHash.get(i).equals(currentBlockHash.get(i - 1))) {
                return false;
            }
        }
        return true;
    }

    // print the chain status
    public void chainStatus() throws IOException {
        if (isChainValid()) {
            System.out.println("Chain Is Valid...");
        } else {
            System.out.println("Chain Is not Valid !!!");
        }
    }

    // mine blocks by find the correct hash by difficulty
    public void mineBlock(int difficulty, String data) throws FileNotFoundException, IOException {
        String target = new String(new char[difficulty]).replace('\0', '0');
        String hash = getLatestHash();
        while (!hash.substring(0, difficulty).equals(target) || hash.equals(getLatestHash())) {
            setNonce(getNonce() + 1);
            hash = calculateBlockHash();
        }
        Block newBlock = new Block(getLatestBlock().getIndex() + 1, data, getLatestHash());
        newBlock.setHash(hash);
        newBlock.setNonce(getNonce());
        blockchain.add(newBlock);
        saveBlockChain();
    }

    // save every new block created by miners into file
    public void saveBlockChain() throws FileNotFoundException, IOException {
        try {
            FileWriter fstream = new FileWriter("file path.txt", true);
            try (BufferedWriter out = new BufferedWriter(fstream)) {
                for (int i = 0; i < getBlockChain().size(); i++) {
                    Block b = (Block) getBlockChain().get(i);
                    out.write(b.getIndex() + "," + b.getHash() + "," + b.getPreviousHash() + "," + b.getData() + "," + b.getNonce() + "," + b.getTimeStamp() + "\n");  //New line
                    out.flush();
                    out.close();
                }
            }
        } catch (IOException e) {
        }
        getBlockChain().clear();
    }
}
