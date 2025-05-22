# Simple Blockchain — Spring Boot Edition

*A teaching playground for hashing, Merkle trees, proof‑of‑work and JWT‑secured APIs*

> **What this repo is** → A fully–self‑contained Java (Spring Boot 3) project that walks through every primitive of a Bitcoin‑style blockchain, but distilled into \~800 LOC so you can step through it in a debugger.
>
> **What it is *not*** → A production‑grade ledger. Nothing here is hardened for Byzantine actors or tuned for throughput. Use it to learn the moving pieces, then graduate to Hyperledger / Tendermint.

---

## 0 · Directory Map

| path                                                              | role                                                                  |
| ----------------------------------------------------------------- | --------------------------------------------------------------------- |
| `pom.xml`                                                         | Spring Boot 3 + Maven build (JDK 17, spring‑security, jjwt)           |
| `src/main/java/com/example/blockChain/BlockChainApplication.java` | Bootstrap – `@SpringBootApplication`                                  |
| `Models/Block.java`                                               | Block header ➜ SHA‑256 hash, Merkle root, nonce, timestamp            |
| `Models/Transaction.java`                                         | from → to, amount, timestamp, txHash                                  |
| `Models/MerkleTree.java`                                          | Builds Merkle root of a block’s tx list                               |
| `BlockChain.java`                                                 | **In‑memory ledger** – create genesis, mine block (PoW), verify chain |
| `service/MyUserDetailsService.java`                               | Hard‑coded demo users for JWT auth                                    |
| `Utils/HashUtil.java`                                             | `sha256(String …)` + helper to calc Merkle root                       |
| `Utils/JwtUtils.java`                                             | Issue & parse HS256 JWTs                                              |
| `Utils/JwtRequestFilter.java`                                     | Spring once‑per‑request filter → sets `SecurityContext`               |
| `Config/SecurityConfig.java`                                      | Stateless JWT security (`/api/**` protected, `/hello` public)         |
| `Controller/HelloController.java`                                 | Demo endpoints: `GET /hello`, `POST /api/mine`, `GET /api/chain`      |
| `application.properties`                                          | Port 8080, JWT secret, H2 disable banner                              |
| `src/test/.../BlockChainApplicationTests.java`                    | Boot context sanity test                                              |
| `.mvn/`, `mvnw*`                                                  | Maven wrapper – no global Maven needed                                |

---

## 1 · Quick Start

```bash
# clone & run
$ git clone https://github.com/<you>/blockChain-java-spring.git
$ cd blockChain-java-spring
$ ./mvnw spring-boot:run              # requires only JDK 17+
```

*Key routes*

| verb   | route           | description                                             |
| ------ | --------------- | ------------------------------------------------------- |
| `GET`  | `/hello`        | open endpoint – returns "Hello, World"                  |
| `POST` | `/authenticate` | pass JSON `{"username":"foo","password":"bar"}` → JWT   |
| `POST` | `/api/tx`       | add transaction (header `Authorization: Bearer <JWT>` ) |
| `POST` | `/api/mine`     | mine a block with current mempool (PoW difficulty = 4)  |
| `GET`  | `/api/chain`    | full blockchain JSON                                    |

---

## 2 · How it Works  (code walkthrough)

### 2.1 Block & PoW

```java
while(!hash.startsWith(repeat("0", difficulty))) {
    nonce++;
    hash = HashUtil.applySha256(calculateBlockData());
}
```

*Nonce* is bumped until the block hash has `difficulty` leading zeros.

### 2.2 Merkle Root

`MerkleTree.build(List<String> txHashes)` → iteratively hashes pairs until one root remains. Stored in `Block.merkleRoot`.

### 2.3 Ledger Verification

`BlockChain.isChainValid()` checks:

* `current.previousHash` chain linkage
* recalculated block hash matches stored hash
* each block’s Merkle root matches its tx list

### 2.4 JWT Flow

1. `/authenticate` → `AuthenticationManager` authenticates against `MyUserDetailsService`.
2. `JwtUtils.generateToken()` returns 30‑min HS256 JWT.
3. Subsequent `/api/**` hits run through `JwtRequestFilter`, which sets `UsernamePasswordAuthenticationToken` in `SecurityContextHolder`.

---

## 3 · Extending the Playground

* 🔐 Swap HS256 for RS256 keys in `JwtUtils`.
* 🌐 Persist the chain: replace `List<Block>` with JPA `@Entity` or Mongo.
* 📡 P2P: add WebSocket broadcast when a new block is mined.
* 🏦 Consensus: bolt on Proof‑of‑Authority; let a Spring `@Scheduled` task rotate proposers.

---

## 4 · Build & Test

```bash
./mvnw clean package      # creates target/blockChain.jar
java -jar target/blockChain.jar

# run JUnit test
./mvnw test
```

---

## 5 · Why it’s useful for an interview / demo

| concept               | demonstrated in code                                |
| --------------------- | --------------------------------------------------- |
| Merkle trees          | `Models/MerkleTree.java` (iterative hashing)        |
| Proof‑of‑Work         | `Block.mineBlock()` loop w/ configurable difficulty |
| JWT + Spring Security | `SecurityConfig`, `JwtRequestFilter`, `JwtUtils`    |
| RESTful API           | `HelloController` with public & secured routes      |
| Clean Maven build     | wrapper + reproducible deps                         |

Show this repo to signal: *“I grok cryptographic primitives, can wire Spring Security, and keep the codebase lean.”*

---

## 6 · License

MIT © Yuvraj Malik — use, fork, learn.
