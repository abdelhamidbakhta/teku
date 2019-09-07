/*
 * Copyright 2019 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package tech.pegasys.artemis.reference.phase0.sanity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.errorprone.annotations.MustBeClosed;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import org.apache.tuweni.junit.BouncyCastleExtension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tech.pegasys.artemis.datastructures.blocks.BeaconBlock;
import tech.pegasys.artemis.datastructures.state.BeaconState;
import tech.pegasys.artemis.reference.TestSuite;
import tech.pegasys.artemis.statetransition.util.BlockProcessingException;
import tech.pegasys.artemis.statetransition.util.BlockProcessorUtil;

@ExtendWith(BouncyCastleExtension.class)
@Disabled
public class blocks extends TestSuite {

  @ParameterizedTest(name = "{index} root of Merkleizable")
  @MethodSource({
    "sanityAttestationSetup",
    "sanityAttesterSlashingSetup",
    "sanityBalanceDrivenTransitionsSetup",
    "sanityDepositInBlockSetup",
    "sanityDepositTopUpSetup",
    "sanityEmptyBlockTransitionSetup",
    "sanityEmptyEpochTransitionSetup",
    "sanityHistoricalBatchSetup",
    "sanityProposerSlashingSetup",
    "sanitySameSlotBlockTransitionSetup",
    "sanitySkippedSlotsSetup",
    "sanityVoluntaryExitSetup"
  })
  void sanityProcessBlock(BeaconState pre, BeaconState post, List<BeaconBlock> blocks) {
    blocks.forEach(
        block -> {
          assertDoesNotThrow(() -> BlockProcessorUtil.process_block_header(pre, block, true));
        });
    assertEquals(pre, post);
  }

  @MustBeClosed
  static Stream<Arguments> sanityAttestationSetup() throws Exception {
    Path configPath = Paths.get("mainnet", "phase0");
    Path path = Paths.get("/mainnet/phase0/blocks/blocks/pyspec_tests/attestation");
    return sanityMultiBlockSetup(path, configPath);
  }

  @MustBeClosed
  static Stream<Arguments> sanityAttesterSlashingSetup() throws Exception {
    Path configPath = Paths.get("mainnet", "phase0");
    Path path = Paths.get("/mainnet/phase0/blocks/blocks/pyspec_tests/attester_slashing");
    return sanityMultiBlockSetup(path, configPath);
  }

  @MustBeClosed
  static Stream<Arguments> sanityBalanceDrivenTransitionsSetup() throws Exception {
    Path configPath = Paths.get("mainnet", "phase0");
    Path path =
        Paths.get("/mainnet/phase0/blocks/blocks/pyspec_tests/balance_driven_status_transitions");
    return sanityMultiBlockSetup(path, configPath);
  }

  @MustBeClosed
  static Stream<Arguments> sanityDepositInBlockSetup() throws Exception {
    Path configPath = Paths.get("mainnet", "phase0");
    Path path = Paths.get("/mainnet/phase0/blocks/blocks/pyspec_tests/deposit_in_block");
    return sanityMultiBlockSetup(path, configPath);
  }

  @MustBeClosed
  static Stream<Arguments> sanityDepositTopUpSetup() throws Exception {
    Path configPath = Paths.get("mainnet", "phase0");
    Path path = Paths.get("/mainnet/phase0/blocks/blocks/pyspec_tests/deposit_top_up");
    return sanityMultiBlockSetup(path, configPath);
  }

  @MustBeClosed
  static Stream<Arguments> sanityEmptyBlockTransitionSetup() throws Exception {
    Path configPath = Paths.get("mainnet", "phase0");
    Path path = Paths.get("/mainnet/phase0/blocks/blocks/pyspec_tests/empty_block_transition");
    return sanityMultiBlockSetup(path, configPath);
  }

  @MustBeClosed
  static Stream<Arguments> sanityEmptyEpochTransitionSetup() throws Exception {
    Path configPath = Paths.get("mainnet", "phase0");
    Path path = Paths.get("/mainnet/phase0/blocks/blocks/pyspec_tests/empty_epoch_transition");
    return sanityMultiBlockSetup(path, configPath);
  }

  @MustBeClosed
  static Stream<Arguments> sanityHistoricalBatchSetup() throws Exception {
    Path configPath = Paths.get("mainnet", "phase0");
    Path path = Paths.get("/mainnet/phase0/blocks/blocks/pyspec_tests/historical_batch");
    return sanityMultiBlockSetup(path, configPath);
  }

  @MustBeClosed
  static Stream<Arguments> sanityProposerSlashingSetup() throws Exception {
    Path configPath = Paths.get("mainnet", "phase0");
    Path path = Paths.get("/mainnet/phase0/blocks/blocks/pyspec_tests/proposer_slashing");
    return sanityMultiBlockSetup(path, configPath);
  }

  @MustBeClosed
  static Stream<Arguments> sanitySameSlotBlockTransitionSetup() throws Exception {
    Path configPath = Paths.get("mainnet", "phase0");
    Path path = Paths.get("/mainnet/phase0/blocks/blocks/pyspec_tests/same_slot_block_transition");
    return sanityMultiBlockSetup(path, configPath);
  }

  @MustBeClosed
  static Stream<Arguments> sanitySkippedSlotsSetup() throws Exception {
    Path configPath = Paths.get("mainnet", "phase0");
    Path path = Paths.get("/mainnet/phase0/blocks/blocks/pyspec_tests/skipped_slots");
    return sanityMultiBlockSetup(path, configPath);
  }

  @MustBeClosed
  static Stream<Arguments> sanityVoluntaryExitSetup() throws Exception {
    Path configPath = Paths.get("mainnet", "phase0");
    Path path = Paths.get("/mainnet/phase0/blocks/blocks/pyspec_tests/voluntary_exit");
    return sanityMultiBlockSetup(path, configPath);
  }

  @ParameterizedTest(name = "{index} root of Merkleizable")
  @MethodSource({
    "sanityInvalidStateRootSetup",
    "sanityExpectedDepositInBlockSetup",
    "sanityPrevSlotBlockTransitionSetup"
  })
  void sanityProcessBlockInvalid(BeaconState pre, List<BeaconBlock> blocks) {
    blocks.forEach(
        block -> {
          assertThrows(
              BlockProcessingException.class,
              () -> BlockProcessorUtil.process_block_header(pre, block, true));
        });
  }

  @MustBeClosed
  static Stream<Arguments> sanityInvalidStateRootSetup() throws Exception {
    Path configPath = Paths.get("mainnet", "phase0");
    Path path = Paths.get("/mainnet/phase0/blocks/blocks/pyspec_tests/invalid_state_root");
    return sanityMultiBlockSetupInvalid(path, configPath);
  }

  @MustBeClosed
  static Stream<Arguments> sanityExpectedDepositInBlockSetup() throws Exception {
    Path configPath = Paths.get("mainnet", "phase0");
    Path path = Paths.get("/mainnet/phase0/blocks/blocks/pyspec_tests/expected_deposit_in_block");
    return sanityMultiBlockSetupInvalid(path, configPath);
  }

  @MustBeClosed
  static Stream<Arguments> sanityPrevSlotBlockTransitionSetup() throws Exception {
    Path configPath = Paths.get("mainnet", "phase0");
    Path path = Paths.get("/mainnet/phase0/blocks/blocks/pyspec_tests/prev_slot_block_transition");
    return sanityMultiBlockSetupInvalid(path, configPath);
  }
}