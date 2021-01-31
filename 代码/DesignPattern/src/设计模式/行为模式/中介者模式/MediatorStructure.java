package 设计模式.行为模式.中介者模式;

/**
 * @author like
 * @date 2020-12-31 13:26
 * @contactMe 980650920@qq.com
 * @description
 */
public class MediatorStructure extends Mediator {

    // 聚合房主和租房者对象
    private HostOwner hostOwner;
    private Tenant tenant;

    public HostOwner getHostOwner() {
        return hostOwner;
    }

    public void setHostOwner(HostOwner hostOwner) {
        this.hostOwner = hostOwner;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    @Override
    public void constanct(String message, Person person) {
        if (person instanceof HostOwner) {
            tenant.getMessage(message);
        } else if (person instanceof Tenant) {
            hostOwner.getMessage(message);
        }else {
            throw new RuntimeException("没有找到对应角色");
        }
    }
}
