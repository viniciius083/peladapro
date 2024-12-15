package com.peladapro.wrappers;

import com.peladapro.dto.vote.VoteDTO;

import java.util.ArrayList;
import java.util.List;

public class VoteListWrapper {
    private List<VoteDTO> votes;

    public List<VoteDTO> getVotes() {
        return votes;
    }

    public void setVotes(List<VoteDTO> votes) {
        this.votes = votes;
    }
}
