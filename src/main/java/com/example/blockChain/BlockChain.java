package com.example.blockChain;
import com.example.blockChain.Models.*;

import java.util.ArrayList;
import java.util.List;

public class BlockChain {

    public List<Block> BlockChain;
    public Integer difficulty;

    public List<Block> getBlockChain(){
        return BlockChain;
    }
    public Integer getDifficulty(){
        return difficulty;
    }

    public BlockChain(){
        BlockChain = new ArrayList<>();
        difficulty = 4;
    }

    private Block createGenisisBlock(){
        List<Transaction> GenisisTransactions = new ArrayList<>();
        GenisisTransactions.add(new Transaction("0", "Genisis", 50.434f) );
        return new Block("0", GenisisTransactions);
    }
    public void addBlock(Block block){
        block.mineBlock(difficulty);
        BlockChain.add(block);
    }
    public Block getLatestBlock(){
        return BlockChain.get(BlockChain.size()-1);
    }

    public boolean isChainValid(){
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for (int i = 1; i < BlockChain.size(); i++) {
            currentBlock = BlockChain.get(i);
            previousBlock = BlockChain.get(i - 1);

            // Compare registered hash and calculated hash
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                System.out.println("Current hashes not equal");
                return false;
            }

            // Compare previous hash and registered previous hash
            if (!previousBlock.getHash().equals(currentBlock.getPrevHash())) {
                System.out.println("Previous hashes not equal");
                return false;
            }

            // Check if hash is solved
            if (!currentBlock.getHash().substring(0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }
        return true;}
}
