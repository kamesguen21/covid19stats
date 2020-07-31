package com.guen.covid19stats.service.impl;

import com.guen.covid19stats.service.ItemService;
import com.guen.covid19stats.domain.Item;
import com.guen.covid19stats.repository.ItemRepository;
import com.guen.covid19stats.repository.search.ItemSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Item}.
 */
@Service
public class ItemServiceImpl implements ItemService {

    private final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

    private final ItemRepository itemRepository;

    private final ItemSearchRepository itemSearchRepository;

    public ItemServiceImpl(ItemRepository itemRepository, ItemSearchRepository itemSearchRepository) {
        this.itemRepository = itemRepository;
        this.itemSearchRepository = itemSearchRepository;
    }

    @Override
    public Item save(Item item) {
        log.debug("Request to save Item : {}", item);
        Item result = itemRepository.save(item);
        itemSearchRepository.save(result);
        return result;
    }

    @Override
    public List<Item> findAll() {
        log.debug("Request to get all Items");
        return itemRepository.findAll();
    }


    @Override
    public Optional<Item> findOne(String id) {
        log.debug("Request to get Item : {}", id);
        return itemRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Item : {}", id);
        itemRepository.deleteById(id);
        itemSearchRepository.deleteById(id);
    }

    @Override
    public List<Item> search(String query) {
        log.debug("Request to search Items for query {}", query);
        return StreamSupport
            .stream(itemSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
