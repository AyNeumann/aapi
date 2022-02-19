package com.example.Aapi.controller;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Aapi.dto.TagDTO;
import com.example.Aapi.entity.Tag;
import com.example.Aapi.service.TagService;

@RestController
@RequestMapping("/tags-management")
public class TagController {
	
	/** Reference to the log4j logger. */
	private static final Logger LOG = LogManager.getLogger();
	
	/** Reference to the Tag Service */
	private TagService tagService;
	
	public TagController(TagService tagService) {
		this.tagService = tagService;
	}
	
	
	/**
	 * Create a list of Tags and return saved Tags.
	 * The list is validated with the Valid annotation in the method parameters
	 * and with the Validated annotation above the class.
	 * If one of the item of the list is invalid throws ConstraintViolationException.
 	 * @param tag Tags to save
	 * @param bindingResult bindingResult spring framework validation interface
	 * @return the saved Tags as List
	 */
	@PostMapping("/tags")
	public List<TagDTO> saveAllTags(@RequestBody @Valid final List<TagDTO> tags, final BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			String message = "Attempt to create a Tags with an invalid list.";
			LOG.warn(message);
			throw new IllegalArgumentException(message);
		}
		
		List<TagDTO> savedTags = tagService.saveTags(tags);
				
		return savedTags;
	}
	
	/**
	 * Retrieve all tag from the database and return paginated data - 50 tag/page.
	 * @param pageNumber number of the page requested - 0 base count
	 * @return required page of Tag - Page<Tag>
	 */
	@GetMapping
	public Page<TagDTO> retrieveAllTags(@RequestParam(name="pageNumber", required = true ) final Integer pageNumber) {
		
		return tagService.retrieveAllTags(pageNumber);
	}
	
	/**
	 * Retrieve the Tag with the matching id.
	 * @param id id of the Tag to retrieve
	 * @return found Tag - Optional<Tag>
	 */
	@GetMapping("tags/{id}")
	public TagDTO retrieveById(@PathVariable(name="id", required = true ) final Long id) {
		
		TagDTO tagToRetrieve = tagService.retrieveTagById(id);
		
		return tagToRetrieve;
	}
	
	/**
	 * Retrieve the Tags with a name which contains the received name. 
	 * @param name required name to find
	 * @return a list of Tags with a name which contains the received name - Set<Tag>
	 */
	@GetMapping("byName")
	public Set<Tag> findByName(@RequestParam(name="name", required = true ) final String name) {
		
		Set<Tag> tagToRetrieve = tagService.findTagByName(name);
		
		return tagToRetrieve;
	}
	
	/**
	 * Retrieve the Tags with an exact matching name. 
	 * @param name required name to find
	 * @return the Tag with an exact matching name. - Tag
	 */
	@GetMapping("retrieveByName")
	public Tag retrieveByName(@RequestParam(name="name", required = true ) final String name) {
		
		Tag tagToRetrieve = tagService.retrieveTagByName(name);
		
		return tagToRetrieve;
	}
	
	/**
	 * Update the Tag with the matching id.
	 * @param tag new Tag data
	 * @param bindingResult spring framework validation interface
	 * @return updated tag or null if tag hasn't been updated - Tag
	 */
	@PutMapping
	public TagDTO updateTag (@RequestBody @Valid final TagDTO tag, final BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			String message = "Attempt to udpate a Tag with invalid data.";
			LOG.warn(message);
			throw new IllegalArgumentException(message);
		}
		
		return tagService.updateTag(tag);
	}
	
	/**
	 * Delete the Tag with the matching type
	 * @param id id of the Tag to delete
	 * @return true if the Tag has been deleted
	 */
	@DeleteMapping
	public boolean deleteTag (@RequestParam(name="id", required = true ) final Long id) {
		
		boolean isDeleted = tagService.deleteTag(id);
		
		return isDeleted;
	}

}
