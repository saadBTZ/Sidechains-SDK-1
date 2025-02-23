**0.5.0**
1. Scorex dependency has been updated from Scorex 2.0.0-RC6 to Sparkz 2.0.0-RC9 (package name has been changed to sparkz).
2. Bootstrapping tool interface changed: cert proof info was separated from signers key generation.
3. Fork manager introduced to be able to implement new backward incompatible functionality.
4. Coin boxes dust check added in the Fork 1.
5. Forward transfer minimum amount limit defined in the Fork 1.
6. Numerous consensus improvements introduced in the Fork 1.
7. OpenStakeTransaction - new core transaction type added to allow the majority of forgers to open staking for everyone. Introduced in the Fork 1.
8. Backward Transfers limit introduced in the Fork 1. The total limit of 3999 BTs per withdrawal epoch, using the "slots" opening strategy per mainchain block reference. Introduced in the Fork 1.
9. Forger block generation fixes: no transactions allowed in case of ommers.
10. Network data checks improved.
11. Network API: connected peers info updated.
12. Numerous library dependencies were updated.

**0.4.3**
1. Blocks network propagation fixed: allow to send blocks greater than 1 Mb.

**0.4.2**
1. Explorer synchronization issue solved: history.chainAfter method was optimized.
2. Certificate commitment tree calculation issue solved: fixed an inconsistency between SC and MC implementations.

**0.4.1**
1. CCTP: other sidechains with version 2+ are supported.
2. API authentication behavior updated: can be disabled now.
3. Swagger API schema fixed.
4. DBTool: custom storages support added.

**0.4.0**
1. Memory pool improvement: upper bound size limit introduced (default 300Mb) altogether with cleanup strategy (the lowest fee rate transaction removed first).
2. Memory pool improvement: minimum fee rate check added for incoming transactions. By default, is disabled.
3. CSW is optional. Sidechains without CSW support are available now. Note: keep using CSW feature in real environment to have a possibility to withdraw coins in case of ceasing.
4. API Authorization added to the coin critical endpoints, like keys management, transaction creation and submission, csw creation, etc.
5. API freezing during node synchronization resolved.
6. New API endpoints added for importing/exporting keys to/from the wallet.
7. Forger sorts transactions by fee rate instead of fee.
8. Wallet: max fee check added for locally generated transactions to prevent absurdly high fees. Max fee value is set in zennies. Default value is 10000000 (0.1 Zen).
9. Custom propositions wallet management improved: complex multi-key propositions are now recognized.
10. Peers spam detection mechanism improved: "trash" data detection in the end of the block/transaction added.
11. Bootstrapping tool: dlog keys multiple initialization prevented.
12. Extra verbosity added to the API responses.
13. Logging system improved. Application specific configs are allowed.
14. FeePayments visibility bug fixed: wrongly added fee payments to the block info when there were no payment at all.

**0.3.5**
1. Snark keys generation fixed: circuit specific segment size added.

**0.3.4**
1. Added the possibility to perform a backup of a sidechain non coin-boxes and restore these boxes into a new bootstrapped sidechain of the same type.
2. log4j version updated.

**0.3.3**
1. Mainchain block deserialization fix: CompactSize usage issue.
2. Bootstrapping tool improvement: scgenesisinfo data parsing.
3. Added logic for checking storages consistency at node startup, and trying to recover the situation for instance if a crash happened during update procedure.
4. CertificateSubmitter on active sync improvement in `getMessageToSign` method.
5. Added HTTP API for stopping the SC node and a hook for calling custom application stop procedure.

**0.3.2**
1. CertificateSubmitter and CertificateSignaturesManager actors restart strategy and failures processing improvement. 

**0.3.1**
1. Withdrawal epoch validator: fix wrongly rejected sidechain block containing McBlockRef with MC2SCAggTx leading to the end of the withdrawal epoch.


**Blaze changes (0.3.0)**
1. New proving system for certificates verification: Coboundary Marlin.
2. PGD: decentralized certificates signing.
3. API updated and improved: in particular certificate submitter, signer, csw and forging.
4. Transaction and Block versioning added for future forks.
5. Timestamp field removed from Transactions.
6. Forgers fee payments mechanism.
7. SidechainCoreTransaction become final. In general transactions structure was improved. Transactions class hierarchy changes.
8. LevelDB key-value storage is used now instead of IODB implementation. IODB was completely removed.
9. Sidechain Test Framework: python version updated from 2 to 3. Multiple improvements.
10. Ceased sidechain withdrawals support.
11. Better logging mechanism. Logging options introduced in the configuration file.
12. Objects serialization improved. New stream-based serialization schema introduced.
13. Sidechain creation versioning support.

**Beta changes**
1. Mainchain synchronization: added backward transfer support with Withdrawal certificate with threshold signature zero-knowledge proof by using [zendoo-sc-cryptolib](https://github.com/HorizenOfficial/zendoo-sc-cryptolib)
2. Added [Latus Proof-of-Stake consensus protocol](https://www.horizen.global/assets/files/Horizen-Sidechain-Zendoo-A_zk-SNARK-Verifiable-Cross-Chain-Transfer-Protocol.pdf)  for sidechain based on [Ouroboros Praos](https://eprint.iacr.org/2017/573.pdf) consensus protocol which supporting forks in Sidechain and Mainchain. Autoforging for Sidechain node is added as well.
3. Reworked Transactions structure: introduced SidechainCoreTransaction, Boxes structure was improved/changed now three types of boxes are present by default: zen box, withdrawal request box and forger box. Forger box is used for consensus forger selection.


**Alpha features**
1. Multiple sidechain nodes network.
2. Mainchain synchronization: Cross-chain Transfer Protocol support for sidechain declaration and forward transfers.
3. Basic Consensus (anyone can forge).
4. Built-in coins transferring operations inside sidechain.
5. HTTP API for basic node operations.
6. Possibility to declare custom Transactions/Boxes/Secrets/etc.
7. Possibility to extend/manage basic API.
8. Web interface and command line tool for interaction with the Node.
9. Sidechain Bootstrapping Tool to configure sidechain network according to the mainchain network.