package springBoot.mvc.practica3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springBoot.mvc.practica3.model.Producto;

import java.util.List;

@Controller
public class ControladorPrincipal {


    @Autowired
    private RestTemplate restTemplate;


    @Value("http://localhost:8095/")
    private String restServerUrl;


    @RequestMapping(value = {"/", "/login"})
    public String login() {
        return "login";
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/index")
    public String select() {
        return "index";
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/newProduct")
    public String newProduct(Model model) {
        model.addAttribute("producto", new Producto());
        return "crearProducto";
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/editProduct/{codigo}")
    public String editProduct(Model model, @PathVariable String codigo) {
        Producto producto = restTemplate.getForObject(restServerUrl + "show/" + codigo, Producto.class);
        model.addAttribute("producto", producto);
        model.addAttribute("editar", true);
        return "crearProducto";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/show/{codigo}")
    public String mostrarProducto(Model model, @PathVariable String codigo) {
        Producto producto = restTemplate.getForObject(restServerUrl + "show/" + codigo, Producto.class);
        model.addAttribute("Producto", producto);
        return "mostrarProductoListaProductos";
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/newProduct")
    public String readGreeting(@ModelAttribute Producto producto) {
        restTemplate.exchange(restServerUrl + "newProduct", HttpMethod.POST, new HttpEntity<>(producto), Producto.class);
        return "mostrarProductoAnadidoCorrecto";
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/delete/{codigo}")
    public String remove(@PathVariable String codigo) {
        restTemplate.delete(restServerUrl + "delete/" + codigo);
        return "redirect:/list";
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping(value = "/list")
    public String getList(Model model) {
        List<Producto> listaProductos = restTemplate.getForObject(restServerUrl + "list", List.class);
        model.addAttribute("listaProductos", listaProductos);
        return "listaProductos";
    }

    @RequestMapping("/403")
    public String accessDenied() {
        return "error/403";
    }

    @RequestMapping("/404")
    public String errorObject() {
        return "errors/404";
    }

    @RequestMapping("/500")
    public String errorServer() {
        return "errors/500";
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping(value = "/listUser")
    public String getListUser(Model model) {
        List<Producto> listaProductos = restTemplate.getForObject(restServerUrl + "list", List.class);
        model.addAttribute("listaProductos", listaProductos);
        return "listaProductosUsuario";
    }

}