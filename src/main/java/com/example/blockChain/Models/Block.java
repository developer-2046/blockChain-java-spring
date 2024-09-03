package com.example.blockChain.Models;
import com.example.blockChain.Utils.HashUtil;
import com.example.blockChain.Models.*;
import com.example.blockChain.Models.Transaction;

import java.util.*;

public class Block {

private String Hash;
private String previousHash;
private List<Transaction> transactions;
private String merkelRoute;
private long timeStamp;
private int nonce;

public String getHash(){
  return Hash;
}
public String getPrevHash(){
  return previousHash;
}
public List<Transaction> getTransactions(){
  return transactions;
}
public String getMerkelRoute(){
  return merkelRoute;
}
public long getTimeStamp(){
  return timeStamp;
}
public int getNonce(){
  return nonce;
}



  public Block(String previousHash, List<Transaction> transactions){
    this.previousHash = previousHash;
    this.transactions = transactions;
    this.timeStamp = new Date().getTime();
    this.Hash = calculateHash();

  }
  public String calculateHash(){
      String dataToHash = previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + transactions.toString();
      HashUtil hashUtil = new HashUtil();
      return hashUtil.applySha256(dataToHash);
  }



  public void mineBlock(int difficulty) {
           String merkleRoot = MerkleTree.getMerkleRoot(transactions);
           String target = new String(new char[difficulty]).replace('\0', '0'); // Create a string with difficulty * "0"
           while(!Hash.substring(0, difficulty).equals(target)) {
               nonce++;
               Hash = calculateHash();
           }
           System.out.println("Block Mined!!! : " + Hash);
       }



}




