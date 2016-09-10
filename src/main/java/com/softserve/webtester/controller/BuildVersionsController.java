package com.softserve.webtester.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.softserve.webtester.model.BuildVersion;
import com.softserve.webtester.service.MetaDataService;
import com.softserve.webtester.validator.BuildVersionValidator;

/**
 * Handles and retrieves {@link BuildVersion} pages depending on the URI template. A user must be log-in
 * first he can access this page.
 *
 * @author Anton Mykytiuk
 */
@Controller
@RequestMapping(value = "/configuration/buildVersions")
public class BuildVersionsController {

    private static final String BUILD_VERSIONS_ATTRIBUTE_NAME = "buildVersions";
    private static final String BUILD_VERSION_ATTRIBUTE_NAME = "buildVersion";
    private static final String PAGE_TASK_ATTRIBUTE_NAME = "pageTask";

    @Autowired
    private MetaDataService metaDataService;

    @Autowired
    private BuildVersionValidator buildVersionValidator;

    @InitBinder("buildVersion")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(buildVersionValidator);
    }

    /**
     * Retrieves page with existing build versions
     *
     * @param model {@link Model} object
     * @return view's name
     */
    @RequestMapping(method = RequestMethod.GET)
    public String getBuildVersionsPage(Model model) {
        List<BuildVersion> buildVersions = metaDataService.loadAllBuildVersions();
        model.addAttribute(BUILD_VERSIONS_ATTRIBUTE_NAME, buildVersions);
        return "buildVersion/buildVersions";
    }

    /**
     * Retrieves page for creating new build version with empty build version instance
     *
     * @param model {@link Model} object
     * @return view's name
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getCreatePage(Model model) {
        model.addAttribute(PAGE_TASK_ATTRIBUTE_NAME, "Create");
        BuildVersion buildVersion = new BuildVersion();
        model.addAttribute(BUILD_VERSION_ATTRIBUTE_NAME, buildVersion);
        return "buildVersion/createOrModify";
    }

    /**
     * Handles creating new build version
     *
     * @param buildVersion {@link BuildVersion} instance should be saved
     * @param result {@link BindingResult} validation handle object
     * @return if success, redirects to build versions main page, in case of validation errors returns to creating page
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String confirmCreate(@Validated @ModelAttribute BuildVersion buildVersion, BindingResult result) {
        if (result.hasErrors()) {
            return "buildVersion/createOrModify";
        }
        metaDataService.saveBuildVersion(buildVersion);
        return "redirect:/configuration/buildVersions";
    }

    /**
     * Retrieves build version modify page
     *
     * @param id identifier of modifing {@link BuildVersion} instance
     * @param model {@link Model} object
     * @return view's name
     */
    @RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
    public String modifyBuildVersion(@PathVariable int id, Model model) {
        model.addAttribute(PAGE_TASK_ATTRIBUTE_NAME, "Modify");
        BuildVersion buildVersion = metaDataService.loadBuildVersionById(id);
        model.addAttribute(BUILD_VERSION_ATTRIBUTE_NAME, buildVersion);
        return "buildVersion/createOrModify";
    }

    /**
     * Handles build version updating
     *
     * @param buildVersion {@link BuildVersion} instance should be updated
     * @param result {@link BindingResult} instance
     * @return if success, redirects to build versions main page; in case of validation errors returns to modifing page
     */
    @RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
    public String confirmModify(@Validated @ModelAttribute BuildVersion buildVersion, BindingResult result) {
        if (result.hasErrors()) {
            return "buildVersion/createOrModify";
        }
        metaDataService.updateBuildVersion(buildVersion);
        return "redirect:/configuration/buildVersions";
    }

    /**
     * Handles build version deleting
     *
     * @param id identifier of {@link BuildVersion} should be deleted
     * @return redirects to build versions main page
     */
    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String deleteBuildVersion(@PathVariable int id) {
        metaDataService.deleteBuildVersion(id);
        return "redirect:/configuration/buildVersions";
    }

}
