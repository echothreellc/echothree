// --------------------------------------------------------------------------------
// Copyright 2002-2026 Echo Three, LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// --------------------------------------------------------------------------------

package com.echothree.util.server.string;

import com.echothree.model.control.party.server.control.PartyControl;
import com.echothree.model.data.party.server.entity.PartyType;
import com.echothree.util.server.persistence.EncryptionUtils;
import com.echothree.util.server.persistence.Session;
import com.echothree.util.server.string.Graph.GraphType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PasswordGeneratorUtils {
    
    private PasswordGeneratorUtils() {
        super();
    }
    
    private static class PasswordGeneratorUtilsHolder {
        static PasswordGeneratorUtils instance = new PasswordGeneratorUtils();
    }
    
    public static PasswordGeneratorUtils getInstance() {
        return PasswordGeneratorUtilsHolder.instance;
    }

    private final static List<Graph> graphs;
    
    static {
        graphs = Collections.unmodifiableList(Arrays.asList(
                new Graph("a", GraphType.VOWEL_SHORT, Collections.unmodifiableList(Arrays.asList("a"))),
                new Graph("A", GraphType.VOWEL_LONG, Collections.unmodifiableList(Arrays.asList("a", "ae", "ai"))),
                new Graph("b", GraphType.CONSONANT, Collections.unmodifiableList(Arrays.asList("b"))),
                new Graph("ch", GraphType.CONSONANT, Collections.unmodifiableList(Arrays.asList("ch"))),
                new Graph("d", GraphType.CONSONANT, Collections.unmodifiableList(Arrays.asList("d"))),
                new Graph("e", GraphType.VOWEL_SHORT, Collections.unmodifiableList(Arrays.asList("e"))),
                new Graph("E", GraphType.VOWEL_LONG, Collections.unmodifiableList(Arrays.asList("e", "ee", "ie"))),
                new Graph("f", GraphType.CONSONANT, Collections.unmodifiableList(Arrays.asList("f", "ph", "gh"))),
                new Graph("g", GraphType.CONSONANT, Collections.unmodifiableList(Arrays.asList("g"))),
                new Graph("h", GraphType.CONSONANT, Collections.unmodifiableList(Arrays.asList("h"))),
                new Graph("i", GraphType.VOWEL_SHORT, Collections.unmodifiableList(Arrays.asList("i", "e"))),
                new Graph("I", GraphType.VOWEL_LONG, Collections.unmodifiableList(Arrays.asList("i", "ai"))),
                new Graph("i", GraphType.VOWEL_OTHER, Collections.unmodifiableList(Arrays.asList("i", "ei"))),
                new Graph("j", GraphType.CONSONANT, Collections.unmodifiableList(Arrays.asList("j", "g"))),
                new Graph("k", GraphType.CONSONANT, Collections.unmodifiableList(Arrays.asList("k", "c"))),
                new Graph("l", GraphType.CONSONANT, Collections.unmodifiableList(Arrays.asList("l"))),
                new Graph("m", GraphType.CONSONANT, Collections.unmodifiableList(Arrays.asList("m"))),
                new Graph("n", GraphType.CONSONANT, Collections.unmodifiableList(Arrays.asList("n"))),
                new Graph("ng", GraphType.CONSONANT, Collections.unmodifiableList(Arrays.asList("ng"))),
                new Graph("o", GraphType.VOWEL_SHORT, Collections.unmodifiableList(Arrays.asList("o", "a", "ah"))),
                new Graph("O", GraphType.VOWEL_LONG, Collections.unmodifiableList(Arrays.asList("o", "oh"))),
                new Graph("oo", GraphType.VOWEL_SHORT, Collections.unmodifiableList(Arrays.asList("oo", "u"))),
                new Graph("OO", GraphType.VOWEL_LONG, Collections.unmodifiableList(Arrays.asList("oo", "w"))),
                new Graph("p", GraphType.CONSONANT, Collections.unmodifiableList(Arrays.asList("p"))),
                new Graph("qu", GraphType.CONSONANT, Collections.unmodifiableList(Arrays.asList("qu"))),
                new Graph("r", GraphType.CONSONANT, Collections.unmodifiableList(Arrays.asList("r"))),
                new Graph("s", GraphType.CONSONANT, Collections.unmodifiableList(Arrays.asList("s", "c"))),
                new Graph("sh", GraphType.CONSONANT, Collections.unmodifiableList(Arrays.asList("sh", "s"))),
                new Graph("t", GraphType.CONSONANT, Collections.unmodifiableList(Arrays.asList("t"))),
                new Graph("th", GraphType.CONSONANT, Collections.unmodifiableList(Arrays.asList("th"))),
                new Graph("TH", GraphType.CONSONANT, Collections.unmodifiableList(Arrays.asList("th"))),
                new Graph("u", GraphType.VOWEL_SHORT, Collections.unmodifiableList(Arrays.asList("u"))),
                new Graph("U", GraphType.VOWEL_LONG, Collections.unmodifiableList(Arrays.asList("u", "oo"))),
                new Graph("v", GraphType.CONSONANT, Collections.unmodifiableList(Arrays.asList("v"))),
                new Graph("x", GraphType.CONSONANT, Collections.unmodifiableList(Arrays.asList("x"))),
                new Graph("y", GraphType.CONSONANT, Collections.unmodifiableList(Arrays.asList("y"))),
                new Graph("z", GraphType.CONSONANT, Collections.unmodifiableList(Arrays.asList("z", "s")))
                ));
    }
    
    private Graph selectGraph(Random random, List<Graph> graphs) {
        return graphs.get(random.nextInt(graphs.size()));
    }
    
    private String selectSpelling(Random random, Graph graph) {
        return graph.spellings.get(random.nextInt(graph.spellings.size()));
    }
    
    private Set<GraphType> chooseNext(Random random, GraphType previous, GraphType current) {
        Set<GraphType> graphTypes;
        
	if(current == GraphType.CONSONANT) {
            graphTypes = EnumSet.of(GraphType.VOWEL_SHORT, GraphType.VOWEL_LONG, GraphType.VOWEL_OTHER);
	} else if(previous == null || previous == GraphType.VOWEL_SHORT || previous == GraphType.VOWEL_LONG || previous == GraphType.VOWEL_OTHER) {
            graphTypes = EnumSet.of(GraphType.CONSONANT);
	} else if(random.nextBoolean()) {
            graphTypes = EnumSet.of(GraphType.VOWEL_SHORT, GraphType.VOWEL_LONG, GraphType.VOWEL_OTHER);
            graphTypes.remove(current);
        } else {
            graphTypes = EnumSet.of(GraphType.CONSONANT);
        }
        
        return graphTypes;
    }
    
    public String getPassword(int minimumLength) {
        var random = EncryptionUtils.getInstance().getRandom();
        Graph previous = null;
        var current = selectGraph(random, graphs);
        var password = new StringBuilder();
        
        while(password.length() < minimumLength) {
            var nextGraphTypes = chooseNext(random, previous == null ? null : previous.graphType, current.graphType);
            List<Graph> nextGraphs = new ArrayList<>(graphs.size());
            
            password.append(selectSpelling(random, current));
            
            graphs.stream().filter((graph) -> nextGraphTypes.contains(graph.graphType)).forEach((graph) -> {
                nextGraphs.add(graph);
            });
            
            previous = current;
            current = selectGraph(random, nextGraphs);
        }
        
        return password.toString();
    }
    
    public String getPassword(PartyType partyType) {
        var partyControl = Session.getModelController(PartyControl.class);
        var partyTypePasswordStringPolicyDetail = partyControl.getPartyTypePasswordStringPolicy(partyType).getLastDetail();
        var intMinimumLength = partyTypePasswordStringPolicyDetail.getMinimumLength();
        var intMaximumLength = partyTypePasswordStringPolicyDetail.getMaximumLength();
        var minimumLength = intMinimumLength == null ? 0 : intMinimumLength;
        var maximumLength = intMaximumLength == null ? 38 : intMaximumLength;
        
        return getPassword(minimumLength + (maximumLength - minimumLength) / 4);
    }
    
}
